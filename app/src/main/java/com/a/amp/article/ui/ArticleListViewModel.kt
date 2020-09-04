package com.a.amp.article.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a.amp.article.data.ArticleRelatedCvDataItem
import com.a.amp.article.data.ArticleRepository
import com.a.amp.article.data.CommentCvDataItem

class ArticleListViewModel : ViewModel() {
    var relatedList = MutableLiveData<MutableList<ArticleRelatedCvDataItem>>()
    var commentList = MutableLiveData<MutableList<CommentCvDataItem>>()

    init {
        commentList.value = mutableListOf()
        relatedList.value = ArrayList()
        // can be used by each of above
    }

    fun fillComment() {
        commentList.value?.clear()
        val article = ArticleRepository()
        article.fillCommentFromRepo(commentList)
    }

    fun fillRelated() {
        relatedList.value?.clear()
        val article = ArticleRepository()
        article.fillRelatedFromRepo(relatedList)
    }

}