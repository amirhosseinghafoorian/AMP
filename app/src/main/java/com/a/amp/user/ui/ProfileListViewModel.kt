package com.a.amp.user.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.a.amp.user.data.UserRepository
import com.a.amp.user.data.WritingCvDataItem

class ProfileListViewModel(application: Application) : AndroidViewModel(application) {
    var writeList = MutableLiveData<MutableList<WritingCvDataItem>>()
    val app = application

    init {
        writeList.value = mutableListOf()
    }

    fun fillWrite() {
        writeList.value?.clear()
        val user = UserRepository(app)
        writeList.value?.let { user.fillWriteFromRepo(it) }
    }

}