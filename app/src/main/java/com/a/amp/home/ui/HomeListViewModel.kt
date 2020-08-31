package com.a.amp.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a.amp.home.data.HomeRelatedCvDataItem

class HomeListViewModel : ViewModel() {
    var summaryList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()
    var relatedList = MutableLiveData<MutableList<HomeRelatedCvDataItem>>()

    init {
        summaryList.value = mutableListOf()
        relatedList.value = ArrayList()
        // can be used by each of above
    }

    fun fillSummary() {
        summaryList.value?.clear()
        repeat(10) {
            summaryList.value?.add(
                HomeRelatedCvDataItem(
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
                HomeRelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0
                )
            )
        }
    }

}