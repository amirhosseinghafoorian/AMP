package com.a.amp.home.data

import androidx.lifecycle.MutableLiveData

class HomeLocal {

    fun fillSummaryFromLocal(summaryList: MutableLiveData<MutableList<HomeRelatedCvDataItem>>) {
        repeat(10) {
            summaryList.value?.add(
                HomeRelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0, false
                )
            )
        }
    }

    fun fillRelatedFromLocal(relatedList: MutableLiveData<MutableList<HomeRelatedCvDataItem>>) {
        repeat(10) {
            relatedList.value?.add(
                HomeRelatedCvDataItem(
                    " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0, false
                )
            )
        }
    }
}