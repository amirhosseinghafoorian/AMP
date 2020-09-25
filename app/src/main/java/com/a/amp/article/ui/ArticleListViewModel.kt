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
import com.a.amp.article.data.ArticleRelatedCvDataItem
import com.a.amp.article.data.ArticleRepository
import com.a.amp.article.data.CommentCvDataItem
import com.a.amp.user.data.WritingCvDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleListViewModel(application: Application) : AndroidViewModel(application) {
    var relatedList = MutableLiveData<MutableList<ArticleRelatedCvDataItem>>()
    var commentList = MutableLiveData<MutableList<CommentCvDataItem>>()
    var singleArticle = MutableLiveData<MutableList<WritingCvDataItem>>()
    var tagList = MutableLiveData<MutableList<String>>()
    val app = application
    var favorited = MutableLiveData<Boolean>()

    init {
        commentList.value = mutableListOf()
        relatedList.value = ArrayList()
        singleArticle.value = mutableListOf()
        tagList.value = mutableListOf()
        // can be used by each of above
    }

    suspend fun fillSingleArticleWithComments(id: String) {
        val article = ArticleRepository(app)
        commentList.postValue(null)
        tagList.postValue(null)
        val res = article.fillSingleArticleWithCommentsFromRepo(id)
        singleArticle.postValue(res[0] as MutableList<WritingCvDataItem>)
        commentList.postValue(res[1] as MutableList<CommentCvDataItem>)
        tagList.postValue(res[2] as MutableList<String>)
    }

    suspend fun fillRelated() {
        relatedList.postValue(null)
        val article = ArticleRepository(app)
        relatedList.postValue(article.fillRelatedFromRepo())
    }

//    suspend fun fillSingleArticle(id: String) {
//        val article = ArticleRepository(app)
//        singleArticle.postValue(article.fillSingleArticleFromRepo(id))
//    }

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