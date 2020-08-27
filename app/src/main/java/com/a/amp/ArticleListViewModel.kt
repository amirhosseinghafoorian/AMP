package com.a.amp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleListViewModel : ViewModel() {
    var relatedList = MutableLiveData<MutableList<RelatedCvDataItem>>()
    var commentList = MutableLiveData<MutableList<CommentCvDataItem>>()

    init {
        commentList.value = mutableListOf()
        relatedList.value = ArrayList()
        // can be used by each of above
    }

    fun fillComment() {
        commentList.value?.clear()
        repeat(10) {
            commentList.value?.add(
                CommentCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", 0
                )
            )
        }
    }

    fun fillRelated() {
        relatedList.value?.clear()
        repeat(10) {
            relatedList.value?.add(
                RelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0
                )
            )
        }
    }

}