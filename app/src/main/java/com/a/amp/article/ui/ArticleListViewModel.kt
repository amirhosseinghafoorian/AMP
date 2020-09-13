package com.a.amp.article.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a.amp.article.data.ArticleRelatedCvDataItem
import com.a.amp.article.data.ArticleRepository
import com.a.amp.article.data.CommentCvDataItem

class ArticleListViewModel(application: Application) : AndroidViewModel(application) {
    var relatedList = MutableLiveData<MutableList<ArticleRelatedCvDataItem>>()
    var commentList = MutableLiveData<MutableList<CommentCvDataItem>>()
    val app = application

    init {
        commentList.value = mutableListOf()
        relatedList.value = ArrayList()
        // can be used by each of above
    }

    fun fillComment() {
        commentList.value?.clear()
        val article = ArticleRepository(app)
        commentList.value?.let { article.fillCommentFromRepo(it) }
    }

    fun fillRelated() {
        relatedList.value?.clear()
        val article = ArticleRepository(app)
        article.fillRelatedFromRepo(relatedList)
    }

}