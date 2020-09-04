package com.a.amp.article.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a.amp.article.data.ArticleRelatedCvDataItem
import com.a.amp.article.data.ArticleRepository

class TagListViewModel : ViewModel() {
    var summaryList = MutableLiveData<MutableList<ArticleRelatedCvDataItem>>()

    init {
        summaryList.value = ArrayList()
    }

    fun fillSummary() {
        summaryList.value?.clear()
        val article = ArticleRepository()
        article.fillRelatedFromRepo(summaryList)
    }

}