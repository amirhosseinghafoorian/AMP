package com.a.amp.home.data

import android.app.Application

class HomeRepository(application: Application) {
    val app = application

    suspend fun fillSummaryFromRepo(): MutableList<HomeRelatedCvDataItem> {
        val home = HomeLocal(app)
        return home.fillSummaryFromLocal()
    }

    suspend fun fillRelatedFromRepo(): MutableList<HomeRelatedCvDataItem> {
        val home = HomeLocal(app)
        return home.fillRelatedFromLocal()
    }

}