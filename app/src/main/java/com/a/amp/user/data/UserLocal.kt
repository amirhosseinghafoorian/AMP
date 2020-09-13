package com.a.amp.user.data

import android.app.Application
import com.a.amp.AppDataBase
import com.a.amp.article.data.ArticleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLocal(application: Application) {

    private val db = AppDataBase.buildDatabase(context = application)

    fun fillWriteFromLocal(writeList: MutableList<WritingCvDataItem>) {
        CoroutineScope(Dispatchers.IO).launch {
            writeList.addAll(ArticleEntity.convertToDataItem2(db.myDao().getArticles()))
        }
    }
}