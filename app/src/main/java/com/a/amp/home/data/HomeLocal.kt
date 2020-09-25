package com.a.amp.home.data

import android.app.Application
import com.a.amp.article.data.ArticleEntity
import com.a.amp.database.AppDataBase

class HomeLocal(application: Application) {

    private val db = AppDataBase.buildDatabase(context = application)

    suspend fun fillSummaryFromLocal(summaryList: MutableList<HomeRelatedCvDataItem>) {
        summaryList.addAll(ArticleEntity.convertToDataItem3(db.myDao().getFeed(true)))
    }

    suspend fun fillRelatedFromLocal(relatedList: MutableList<HomeRelatedCvDataItem>) {
        relatedList.addAll(ArticleEntity.convertToDataItem3(db.myDao().getArticles()))
    }
}