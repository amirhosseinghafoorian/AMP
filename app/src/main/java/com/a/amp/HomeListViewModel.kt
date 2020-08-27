package com.a.amp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeListViewModel : ViewModel() {
    var summaryList = MutableLiveData<MutableList<RelatedCvDataItem>>()
    var relatedList = MutableLiveData<MutableList<RelatedCvDataItem>>()

    init {
        summaryList.value = mutableListOf()
        relatedList.value = ArrayList()
        // can be used by each of above
    }

    fun fillSummary() {
        summaryList.value?.clear()
        repeat(10) {
            summaryList.value?.add(
                it,
                RelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0
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