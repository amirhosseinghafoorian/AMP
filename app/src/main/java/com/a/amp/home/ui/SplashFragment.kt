package com.a.amp.home.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.a.amp.MyApp
import com.a.amp.R
import com.a.amp.article.apimodel2.Article
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleRepository
import com.a.amp.core.resource.Status
import com.a.amp.database.AppDataBase
import com.a.amp.storage.setting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SplashFragment : Fragment() {

    private var currentUser = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope((Dispatchers.IO)).launch {
            fillDataBase()
        }
        val setting = setting()
        currentUser = setting.getString("username")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            activity?.window?.decorView?.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(context, currentUser, Toast.LENGTH_SHORT).show()
        val handler = Handler()
        handler.postDelayed({
            if (currentUser == "***" || currentUser == "") {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthenticate())
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }
        }, 3000)
    }

    private suspend fun fillDataBase() {

        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        val repo = ArticleRepository(MyApp.publicApp)

        Looper.prepare()
        val repoResult = repo.syncArticles()
        if (repoResult.status == Status.SUCCESS && repoResult.code == 200) {
            val unformattedList = repoResult.data?.articles
            val formattedList = mutableListOf<Article>()
            unformattedList?.let { formattedList.addAll(it) }
            val resultList = ArticleEntity.convertToDataItem4(formattedList)
            for (i in resultList.indices) {
                db.myDao().insertArticles(resultList[i])
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "بروزرسانی انجام شد", Toast.LENGTH_SHORT).show()
            }
        } else if (repoResult.status == Status.SUCCESS && repoResult.code != 200) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
            }
        } else if (repoResult.status == Status.ERROR) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show()
            }
        }


    }
}