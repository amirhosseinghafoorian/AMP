package com.a.amp.home.data

import android.app.Application
import com.a.amp.AppDataBase
import com.a.amp.article.data.ArticleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeLocal(application: Application) {

    private val db = AppDataBase.buildDatabase(context = application)

    fun fillSummaryFromLocal(summaryList: MutableList<HomeRelatedCvDataItem>) {
        CoroutineScope(Dispatchers.IO).launch {
            summaryList.addAll(ArticleEntity.convertToDataItem3(db.myDao().getArticles()))
        }
    }

    fun fillRelatedFromLocal(relatedList: MutableList<HomeRelatedCvDataItem>) {
        CoroutineScope(Dispatchers.IO).launch {
            relatedList.addAll(ArticleEntity.convertToDataItem3(db.myDao().getArticles()))
        }
    }
}