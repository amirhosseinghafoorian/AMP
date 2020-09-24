package com.a.amp.article.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.amp.MyApp
import com.a.amp.article.data.*
import com.a.amp.core.resource.Status
import com.a.amp.database.AppDataBase
import com.a.amp.user.data.WritingCvDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleListViewModel(application: Application) : AndroidViewModel(application) {
    var relatedList = MutableLiveData<MutableList<ArticleRelatedCvDataItem>>()
    var commentList = MutableLiveData<MutableList<CommentCvDataItem>>()
    var singleArticle = MutableLiveData<MutableList<WritingCvDataItem>>()
    val app = application
    var favorited = MutableLiveData<Boolean>()

    init {
        commentList.value = mutableListOf()
        relatedList.value = ArrayList()
        singleArticle.value = mutableListOf()
        // can be used by each of above
    }

    suspend fun fillComment(slug: String) {
        commentList.postValue(null)
        val article = ArticleRepository(app)
        val result = article.getSingleArticleCommentsFromRepo(slug)
        if (result.status == Status.SUCCESS && result.code == 200) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "بروزرسانی انجام شد", Toast.LENGTH_SHORT)
                    .show()
            }
            val db = AppDataBase.buildDatabase(MyApp.publicApp)
            val unformattedList = result.data?.comments
            val formattedList = unformattedList?.let { CommentEntity.convertToDataItem2(it, slug) }
            for (i in 0 until formattedList?.size!!) {
                db.myDao().insertComments(formattedList[i])
            }

        } else if (result.status == Status.SUCCESS && result.code != 200) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "خطا", Toast.LENGTH_SHORT)
                    .show()
            }
        } else if (result.status == Status.ERROR) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        commentList.postValue(article.fillCommentFromRepo(slug))
    }

    suspend fun fillRelated() {
        relatedList.postValue(null)
        val article = ArticleRepository(app)
        relatedList.postValue(article.fillRelatedFromRepo())
    }

    suspend fun fillSingleArticle(id: String) {
        val article = ArticleRepository(app)
        singleArticle.postValue(article.fillSingleArticleFromRepo(id))
    }

    fun favoriteArticle(slug: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val favArt = ArticleRepository(app).favoriteArticle(slug)
            if (favArt.code == 200){favorited.postValue(true)}
        }

    }

    fun unFavoriteArticle(slug: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val favArt = ArticleRepository(app).unFavoriteArticle(slug)
            if (favArt.code == 200){favorited.postValue(false)}
        }

    }

    fun getFavoriteFromServer(slug: String){
        viewModelScope.launch(Dispatchers.IO) {
            val resFavorite = ArticleRemote().getSingleArticleBySlug(slug)
            favorited.postValue(resFavorite.data?.article?.favorited)
        }
    }

}