package com.a.amp.user.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a.amp.MyApp
import com.a.amp.article.apimodel2.Article
import com.a.amp.article.data.ArticleEntity
import com.a.amp.core.resource.Status
import com.a.amp.database.AppDataBase
import com.a.amp.user.data.UserRemote
import com.a.amp.user.data.UserRepository
import com.a.amp.user.data.WritingCvDataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileListViewModel(application: Application) : AndroidViewModel(application) {
    var writeList = MutableLiveData<MutableList<WritingCvDataItem>>()
    val app = application

    init {
        writeList.value = mutableListOf()
    }

    fun fillWrite(username: String) {
        writeList.value?.clear()
        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        val ut = UserRemote()
        CoroutineScope(Dispatchers.IO).launch {
            val repoResult = ut.getArticlesByAuthorFromServer(username)
            if (repoResult.status == Status.SUCCESS && repoResult.code == 200) {
                db.myDao().deleteArticlesByAuthor(username)
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
                    Toast.makeText(MyApp.publicApp, "خطا", Toast.LENGTH_SHORT).show()
                }
            } else if (repoResult.status == Status.ERROR) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            val user = UserRepository(app)
            writeList.postValue(user.fillWriteFromRepo(username))
        }
    }

}