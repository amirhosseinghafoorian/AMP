package com.a.amp.article.data

import androidx.lifecycle.MutableLiveData

class ArticleLocal {

    fun fillCommentFromLocal(CommentList: MutableLiveData<MutableList<CommentCvDataItem>>) {
        repeat(10) {
            CommentList.value?.add(
                CommentCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", 0
                )
            )
        }
    }

    fun fillRelatedFromLocal(relatedList: MutableLiveData<MutableList<ArticleRelatedCvDataItem>>) {
        repeat(10) {
            relatedList.value?.add(
                ArticleRelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0, false
                )
            )
        }
    }
}