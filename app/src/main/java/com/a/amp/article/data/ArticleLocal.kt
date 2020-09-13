package com.a.amp.article.data

import android.app.Application
import com.a.amp.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleLocal(application: Application) {

    private val db = AppDataBase.buildDatabase(context = application)

    fun fillCommentFromLocal(CommentList: MutableList<CommentCvDataItem>) {
        CoroutineScope(Dispatchers.IO).launch {
            CommentList.addAll(CommentEntity.convertToDataItem(db.myDao().getComments()))
        }

    }

    fun fillRelatedFromLocal(relatedList: MutableList<ArticleRelatedCvDataItem>) {
        CoroutineScope(Dispatchers.IO).launch {
            relatedList.addAll(ArticleEntity.convertToDataItem1(db.myDao().getArticles()))
        }
    }
}