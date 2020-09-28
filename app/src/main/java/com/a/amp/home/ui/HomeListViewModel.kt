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
    }

    suspend fun fillSummary(text: String) {
        summaryList.postValue(null)
        val home = HomeRepository(app)
        summaryList.postValue(home.fillSummaryFromRepo(text))
    }

    suspend fun fillRelated() {
        relatedList.postValue(null)
        val home = HomeRepository(app)
        relatedList.postValue(home.fillRelatedFromRepo())
    }

}