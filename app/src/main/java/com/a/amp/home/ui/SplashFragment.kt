package com.a.amp.home.ui

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.a.amp.AppDataBase
import com.a.amp.R
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.CommentEntity
import com.a.amp.home.data.HomeRepository
import com.a.amp.user.data.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private var preferences: SharedPreferences? = null
    private var currentUser = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        publicContext = Application()
        lifecycleScope.launch(Dispatchers.IO) {
            fillDataBase()
            preferences = activity?.getSharedPreferences("locals", MODE_PRIVATE)
            currentUser = preferences?.getString("username", "***").toString()
        }
    }

    companion object {
        lateinit var publicContext: Application
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
            if (currentUser == "***") {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAuthenticate())
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }
        }, 3000)
    }

    private suspend fun fillDataBase() {

        val repo: HomeRepository

        val db = AppDataBase.buildDatabase(context = requireContext())

        db.myDao().insertUsers(
            UserEntity(101, "javad"),
            UserEntity(102, "ali"),
            UserEntity(103, "reza"),
            UserEntity(104, "yasi"),
            UserEntity(105, "amir"),
            UserEntity(106, "nazi"),
            UserEntity(107, "milad"),
            UserEntity(108, "amin"),
            UserEntity(109, "sirus"),
            UserEntity(110, "sajjad"),
        )

        db.myDao().insertArticles(
            ArticleEntity(201, 101, "A simple paper 1", "This is a sample 1"),
            ArticleEntity(202, 105, "A simple paper 2", "This is a sample 2"),
            ArticleEntity(203, 107, "A simple paper 3", "This is a sample 3"),
            ArticleEntity(204, 102, "A simple paper 4", "This is a sample 4"),
            ArticleEntity(205, 103, "A simple paper 5", "This is a sample 5"),
            ArticleEntity(206, 102, "A simple paper 6", "This is a sample 6"),
            ArticleEntity(207, 105, "A simple paper 7", "This is a sample 7"),
            ArticleEntity(208, 105, "A simple paper 8", "This is a sample 8"),
            ArticleEntity(209, 106, "A simple paper 9", "This is a sample 9"),
            ArticleEntity(210, 110, "A simple paper 10", "This is a sample 10"),
        )

        db.myDao().insertComments(
            CommentEntity(301, 203, "nice article 1"),
            CommentEntity(302, 205, "nice article 2"),
            CommentEntity(303, 206, "nice article 3"),
            CommentEntity(304, 202, "nice article 4"),
            CommentEntity(305, 202, "nice article 5"),
            CommentEntity(306, 209, "nice article 6"),
            CommentEntity(307, 203, "nice article 7"),
            CommentEntity(308, 207, "nice article 8"),
            CommentEntity(309, 209, "nice article 9"),
            CommentEntity(310, 204, "nice article 10"),
        )
    }
}