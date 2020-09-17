package com.a.amp.article.data

import android.app.Application
import com.a.amp.article.apimodel2.ArticleResponse
import com.a.amp.core.resource.Resource

class ArticleRepository(application: Application) {
    val app = application

    fun fillCommentFromRepo(commentList: MutableList<CommentCvDataItem>) {
        val article = ArticleLocal(app)
        article.fillCommentFromLocal(commentList)
    }

    fun fillRelatedFromRepo(RelatedList: MutableList<ArticleRelatedCvDataItem>) {
        val article = ArticleLocal(app)
        article.fillRelatedFromLocal(RelatedList)
    }

    suspend fun syncArticles(): Resource<ArticleResponse> {
        val remote = ArticleRemote()
        return remote.getArticlesFromServer()
    }
}