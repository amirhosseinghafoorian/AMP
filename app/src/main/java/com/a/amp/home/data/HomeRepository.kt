package com.a.amp.home.data

import android.app.Application

class HomeRepository(application: Application) {
    val app = application

    fun fillSummaryFromRepo(summaryList: MutableList<HomeRelatedCvDataItem>) {
        val home = HomeLocal(app)
        home.fillSummaryFromLocal(summaryList)
    }

    fun fillRelatedFromRepo(RelatedList: MutableList<HomeRelatedCvDataItem>) {
        val home = HomeLocal(app)
        home.fillRelatedFromLocal(RelatedList)
    }
}