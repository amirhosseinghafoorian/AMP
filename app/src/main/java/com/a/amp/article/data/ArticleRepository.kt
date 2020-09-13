package com.a.amp.article.data

import android.app.Application

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
}