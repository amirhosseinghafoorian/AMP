package com.a.amp.user.data

import androidx.lifecycle.MutableLiveData

class UserLocal {

    fun fillWriteFromLocal(writeList: MutableLiveData<MutableList<WritingCvDataItem>>) {
        repeat(10) {
            writeList.value?.add(
//                WritingCvDataItem("title $it" , "main text $it" , "user $it" ,
//                "$it days ago" , 0 , false , false , 0)
                WritingCvDataItem(
                    " سه خط مقاله : $it", " دو خط مقاله : $it",
                    " نام کاربر : $it", "$it روز پیش ", 0, false, false, 0
                )
            )
        }
    }
}