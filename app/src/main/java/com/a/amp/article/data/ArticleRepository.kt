package com.a.amp.article.data

import android.app.Application
import androidx.lifecycle.MutableLiveData

class ArticleRepository(application: Application) {
    val app = application

    fun fillCommentFromRepo(commentList: MutableList<CommentCvDataItem>) {
        val article = ArticleLocal(app)
        article.fillCommentFromLocal(commentList)
    }

    fun fillRelatedFromRepo(RelatedList: MutableLiveData<MutableList<ArticleRelatedCvDataItem>>) {
        val article = ArticleLocal(app)
        article.fillRelatedFromLocal(RelatedList)
    }
}