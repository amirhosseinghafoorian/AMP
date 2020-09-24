package com.a.amp.article.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a.amp.article.data.ArticleRelatedCvDataItem
import com.a.amp.article.data.ArticleRepository
import com.a.amp.article.data.CommentCvDataItem
import com.a.amp.user.data.WritingCvDataItem

class ArticleListViewModel(application: Application) : AndroidViewModel(application) {
    var relatedList = MutableLiveData<MutableList<ArticleRelatedCvDataItem>>()
    var commentList = MutableLiveData<MutableList<CommentCvDataItem>>()
    var singleArticle = MutableLiveData<MutableList<WritingCvDataItem>>()
    var tagList = MutableLiveData<MutableList<String>>()
    val app = application

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

}