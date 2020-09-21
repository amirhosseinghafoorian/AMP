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
}