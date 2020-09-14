package com.a.amp.user.data

import android.app.Application

class UserRepository(application: Application) {
    val app = application

    fun fillWriteFromRepo(writeList: MutableList<WritingCvDataItem>) {
        val user = UserLocal(application = app)
        user.fillWriteFromLocal(writeList)
    }

    suspend fun loginResult(user: String, pass: String): Boolean {
        val remote = UserRemote()
        return remote.loginFromServer(user, pass)
    }
}