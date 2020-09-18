package com.a.amp.user.data

import android.app.Application
import com.a.amp.core.resource.Resource
import com.a.amp.user.apimodel1.LoginResponse

class UserRepository(application: Application) {
    val app = application

    fun fillWriteFromRepo(writeList: MutableList<WritingCvDataItem>, username: String) {
        val user = UserLocal(application = app)
        user.fillWriteFromLocal(writeList, username)
    }

    suspend fun loginResult(user: String, pass: String): Resource<LoginResponse> {
        val remote = UserRemote()
        return remote.loginFromServer(user, pass)
    }

    suspend fun signUpResult(user: String, pass: String, email: String): Resource<LoginResponse> {
        val remote = UserRemote()
        return remote.registerInServer(user, pass, email)
    }
}