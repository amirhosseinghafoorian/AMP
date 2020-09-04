package com.a.amp.user.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a.amp.user.data.UserRepository
import com.a.amp.user.data.WritingCvDataItem

class ProfileListViewModel : ViewModel() {
    var writeList = MutableLiveData<MutableList<WritingCvDataItem>>()

    init {
        writeList.value = mutableListOf()
    }

    fun fillWrite() {
        writeList.value?.clear()
        val user = UserRepository()
        user.fillWriteFromRepo(writeList)
    }

}