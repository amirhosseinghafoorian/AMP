package com.a.amp.home.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a.amp.home.data.HomeRelatedCvDataItem
import com.a.amp.home.data.HomeRepository

class HomeListViewModel(application: Application) : AndroidViewModel(application) {
    var summaryList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()
    var relatedList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()
    val app = application
    var tagList: MutableLiveData<MutableList<String>>? = null

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

    suspend fun getTags(){
        val response = HomeRepository(app).getAllTagFromServer()
        Log.i("bang", response.data.toString())
        tagList?.postValue(response.data as MutableList<String>?)
    }
}