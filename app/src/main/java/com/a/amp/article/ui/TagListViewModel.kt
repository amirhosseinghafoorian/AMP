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
        summaryList.value = mutableListOf()
    }

    suspend fun fillSummary() {
        summaryList.postValue(null)
        val article = ArticleRepository(app)
        summaryList.postValue(article.fillRelatedFromRepo())
    }

}