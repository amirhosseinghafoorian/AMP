package com.a.amp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileListViewModel : ViewModel() {
    var writeList = MutableLiveData<MutableList<WritingCvDataItem>>()

    init {
        writeList.value = mutableListOf()
    }

    fun fillWrite() {
        writeList.value?.clear()
        repeat(10) {
            writeList.value?.add(
                WritingCvDataItem(
                    " سه خط مقاله : $it", " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0
                )
            )
        }
    }

}