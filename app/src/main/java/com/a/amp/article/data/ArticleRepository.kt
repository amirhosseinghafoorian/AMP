package com.a.amp.article.data

import android.app.Application
import com.a.amp.article.apimodel2.ArticleResponse
import com.a.amp.core.resource.Resource
import com.a.amp.user.data.WritingCvDataItem

class ArticleRepository(application: Application) {
    val app = application

    suspend fun fillCommentFromRepo(commentList: MutableList<CommentCvDataItem>) {
        val article = ArticleLocal(app)
        article.fillCommentFromLocal(commentList)
    }

    suspend fun fillRelatedFromRepo(): MutableList<ArticleRelatedCvDataItem> {
        val article = ArticleLocal(app)
        return article.fillRelatedFromLocal()
    }

    suspend fun fillSingleArticleFromRepo(id: String): MutableList<WritingCvDataItem> {
        val article = ArticleLocal(app)
        return article.fillSingleFromLocal(id)
    }

    suspend fun syncArticles(): Resource<ArticleResponse> {
        val remote = ArticleRemote()
        return remote.getArticlesFromServer()
    }
}