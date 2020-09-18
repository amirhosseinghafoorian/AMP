package com.a.amp.user.data

import android.app.Application
import com.a.amp.article.data.ArticleEntity
import com.a.amp.database.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLocal(application: Application) {

    private val db = AppDataBase.buildDatabase(context = application)

    fun fillWriteFromLocal(writeList: MutableList<WritingCvDataItem>, username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            writeList.addAll(
                ArticleEntity.convertToDataItem2(
                    db.myDao().getArticlesByAuthor(username)
                )
            )
        }
    }
}