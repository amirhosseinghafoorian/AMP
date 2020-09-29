package com.a.amp.user.data

import android.app.Application
import com.a.amp.article.data.ArticleEntity
import com.a.amp.database.AppDataBase

class UserLocal(application: Application) {

    private val db = AppDataBase.buildDatabase(context = application)

    suspend fun fillWriteFromLocal(username: String): MutableList<WritingCvDataItem> {
        return ArticleEntity.convertToDataItem2(
            db.myDao().getArticlesByAuthor(username)
        )
    }

    suspend fun fillLikedFromLocal(): MutableList<WritingCvDataItem> {
        return ArticleEntity.convertToDataItem2(
            db.myDao().getLikedArticles(true)
        )
    }

    suspend fun fillOthersLikedFromLocal(username: String): MutableList<WritingCvDataItem> {
        val slugs = db.myDao().getUserFavs(username)
        val list = mutableListOf<WritingCvDataItem>()
        for (i in slugs.indices) {
            val b = ArticleEntity.convertToDataItem2(
                db.myDao().getArticlesById(slugs[i].connectedArticleId)
            )
            list.addAll(b)
        }
        return list
    }
}