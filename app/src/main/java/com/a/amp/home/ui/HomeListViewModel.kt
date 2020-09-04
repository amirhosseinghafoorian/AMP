package com.a.amp.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a.amp.home.data.HomeRelatedCvDataItem
import com.a.amp.home.data.HomeRepository

class HomeListViewModel : ViewModel() {
    var summaryList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()
    var relatedList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()

    init {
        summaryList.value = mutableListOf()
        relatedList.value = ArrayList()
        // can be used by each of above
    }

    fun fillSummary() {
        summaryList.value?.clear()
        val home = HomeRepository()
        home.fillSummaryFromRepo(summaryList)
    }

    fun fillRelated() {
        relatedList.value?.clear()
        val home = HomeRepository()
        home.fillRelatedFromRepo(relatedList)
    }

}