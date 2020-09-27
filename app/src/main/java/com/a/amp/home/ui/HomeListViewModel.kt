package com.a.amp.home.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a.amp.home.data.HomeRelatedCvDataItem
import com.a.amp.home.data.HomeRepository

class HomeListViewModel(application: Application) : AndroidViewModel(application) {
    var summaryList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()
    var relatedList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()
    val app = application

    init {
        summaryList.value = mutableListOf()
        relatedList.value = ArrayList()
        // can be used by each of above
    }

    suspend fun fillSummary() {
        summaryList.value?.clear()
        val home = HomeRepository(app)
        summaryList.value?.let { home.fillSummaryFromRepo(it) }
    }

    suspend fun fillRelated() {
        relatedList.value?.clear()
        val home = HomeRepository(app)
        relatedList.value?.let { home.fillRelatedFromRepo(it) }
        // Todo edit this and change them to post value
    }

}