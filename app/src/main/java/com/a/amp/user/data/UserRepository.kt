package com.a.amp.user.data

import androidx.lifecycle.MutableLiveData

class UserRepository {

    fun fillWriteFromRepo(writeList: MutableLiveData<MutableList<WritingCvDataItem>>) {
        val user = UserLocal()
        user.fillWriteFromLocal(writeList)
    }
}