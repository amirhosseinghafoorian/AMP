package com.a.amp.home.data

import android.app.Application
import com.a.amp.core.resource.Resource
import com.a.amp.home.apimodel.TagModel

class HomeRepository(application: Application) {
    val app = application

    suspend fun fillSummaryFromRepo(text: String): MutableList<HomeRelatedCvDataItem> {
        val home = HomeLocal(app)
        return home.fillSummaryFromLocal(text)
    }

    suspend fun fillRelatedFromRepo(): MutableList<HomeRelatedCvDataItem> {
        val home = HomeLocal(app)
        return home.fillRelatedFromLocal()
    }

    suspend fun getAllTagFromServer(): Resource<TagModel> {
        val res = HomeRemote()
        return res.getTagFromServer()
    }

}