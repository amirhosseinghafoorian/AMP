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
    val app = application

    init {
        commentList.value = mutableListOf()
        relatedList.value = ArrayList()
        singleArticle.value = mutableListOf()
        // can be used by each of above
    }

    suspend fun fillComment() {
        commentList.value?.clear()
        val article = ArticleRepository(app)
        commentList.value?.let { article.fillCommentFromRepo(it) }
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

}