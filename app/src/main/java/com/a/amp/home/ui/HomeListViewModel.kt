package com.a.amp.home.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a.amp.MyApp
import com.a.amp.article.data.AvailableTagEntity
import com.a.amp.core.resource.Status
import com.a.amp.database.AppDataBase
import com.a.amp.home.data.HomeRelatedCvDataItem
import com.a.amp.home.data.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeListViewModel(application: Application) : AndroidViewModel(application) {
    var summaryList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()
    var relatedList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()
    var tagList = MutableLiveData<MutableList<String>>()
    val app = application

    init {
        summaryList.value = mutableListOf()
        relatedList.value = ArrayList()
        tagList.value = mutableListOf()
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

    suspend fun getTags() {
        tagList.postValue(null)
        val repoResult = HomeRepository(app).getAllTagFromServer()
        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        if (repoResult.status == Status.SUCCESS &&
            repoResult.code == 200
        ) {
            for (i in 0 until repoResult.data?.tags?.size!!) {
                db.myDao().insertAvailableTags(AvailableTagEntity(repoResult.data.tags[i]))
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "بروزرسانی انجام شد", Toast.LENGTH_SHORT).show()
            }
        } else if (repoResult.status == Status.SUCCESS && repoResult.code != 200) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "خطا", Toast.LENGTH_SHORT).show()
            }
        } else if (repoResult.status == Status.ERROR) {
            withContext(Dispatchers.Main) {
                Toast.makeText(MyApp.publicApp, "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show()
            }
        }
        tagList.postValue(AvailableTagEntity.convertToDataItem(db.myDao().getAllAvailableTags()))
    }
}