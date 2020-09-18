package com.a.amp.article.data

import android.app.Application
import com.a.amp.MyApp
import com.a.amp.database.AppDataBase
import com.a.amp.user.data.WritingCvDataItem

class ArticleLocal(application: Application) {

    private val db = AppDataBase.buildDatabase(context = application)

    suspend fun fillCommentFromLocal(CommentList: MutableList<CommentCvDataItem>) {
//        CoroutineScope(Dispatchers.IO).launch {
        CommentList.addAll(CommentEntity.convertToDataItem(db.myDao().getComments()))
//        }

    }

    suspend fun fillRelatedFromLocal(): MutableList<ArticleRelatedCvDataItem> {
//        CoroutineScope(Dispatchers.IO).launch {
        return ArticleEntity.convertToDataItem1(db.myDao().getArticles())
//        }
    }

    suspend fun fillSingleFromLocal(id: String): MutableList<WritingCvDataItem> {
        val db = AppDataBase.buildDatabase(context = MyApp.publicApp)
        return ArticleEntity.convertToDataItem2(db.myDao().getSingleArticleById(id))
    }
}