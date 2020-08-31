package com.a.amp.article.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a.amp.RelatedCvDataItem

class TagListViewModel : ViewModel() {
    var summaryList = MutableLiveData<MutableList<RelatedCvDataItem>>()

    init {
        summaryList.value = ArrayList()
    }

    fun fillSummary() {
        summaryList.value?.clear()
        repeat(10) {
            summaryList.value?.add(
                RelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0
                )
            )
        }
    }

}