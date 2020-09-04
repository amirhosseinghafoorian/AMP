package com.a.amp.home.data

import androidx.lifecycle.MutableLiveData

class HomeRepository {

    fun fillSummaryFromRepo(summaryList: MutableLiveData<MutableList<HomeRelatedCvDataItem>>) {
        val home = HomeLocal()
        home.fillSummaryFromLocal(summaryList)
    }

    fun fillRelatedFromRepo(RelatedList: MutableLiveData<MutableList<HomeRelatedCvDataItem>>) {
        val home = HomeLocal()
        home.fillRelatedFromLocal(RelatedList)
    }
}