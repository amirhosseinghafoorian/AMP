package com.a.amp.article.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a.amp.article.data.ArticleRelatedCvDataItem
import com.a.amp.article.data.ArticleRepository

class TagListViewModel(application: Application) : AndroidViewModel(application) {
    var summaryList = MutableLiveData<MutableList<ArticleRelatedCvDataItem>>()
    val app = application

    init {
        summaryList.value = ArrayList()
    }

    fun fillSummary() {
        summaryList.value?.clear()
        val article = ArticleRepository(app)
        article.fillRelatedFromRepo(summaryList)
    }

}