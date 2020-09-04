package com.a.amp.article.data

import androidx.lifecycle.MutableLiveData

class ArticleRepository {

    fun fillCommentFromRepo(commentList: MutableLiveData<MutableList<CommentCvDataItem>>) {
        val article = ArticleLocal()
        article.fillCommentFromLocal(commentList)
    }

    fun fillRelatedFromRepo(RelatedList: MutableLiveData<MutableList<ArticleRelatedCvDataItem>>) {
        val article = ArticleLocal()
        article.fillRelatedFromLocal(RelatedList)
    }
}