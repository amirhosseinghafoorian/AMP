package com.a.amp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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