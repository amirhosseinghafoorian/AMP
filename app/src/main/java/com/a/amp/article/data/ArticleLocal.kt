package com.a.amp.article.data

import android.app.Application
import com.a.amp.database.AppDataBase
import com.a.amp.user.data.WritingCvDataItem

class ArticleLocal(application: Application) {

    private val db = AppDataBase.buildDatabase(context = application)

    suspend fun fillCommentFromLocal(slug: String): MutableList<CommentCvDataItem> {
        val resultList = mutableListOf<CommentEntity>()
        resultList.addAll(db.myDao().getArticleWithCommentsAndTags(slug)[0].comments)
        return CommentEntity.convertToDataItem(resultList)
    }

    suspend fun fillTagFromLocal(slug: String): MutableList<String> {
        val resultList = mutableListOf<String>()
        val a = db.myDao().getArticleWithCommentsAndTags(slug)[0].tags
        for (i in a.indices) {
            a[i].body?.let { resultList.add(it) }
        }
        return resultList
    }

    suspend fun fillRelatedFromLocal(): MutableList<ArticleRelatedCvDataItem> {
        return ArticleEntity.convertToDataItem1(db.myDao().getArticles())
    }

    suspend fun fillRelatedFromLocal2(text: String): MutableList<ArticleRelatedCvDataItem> {
        return ArticleEntity.convertToDataItem1(db.myDao().getArticlesInTag(text))
    }

    suspend fun fillSingleArticleFromLocal(slug: String): MutableList<WritingCvDataItem> {
        val resultList = mutableListOf<ArticleEntity>()
        resultList.add(db.myDao().getArticleWithCommentsAndTags(slug)[0].article)
        return ArticleEntity.convertToDataItem2(resultList)
    }
}