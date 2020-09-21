package com.a.amp.user.data

import android.app.Application
import com.a.amp.core.resource.Resource
import com.a.amp.user.apimodel1.LoginResponse

class UserRepository(application: Application) {
    val app = application

    suspend fun fillWriteFromRepo(username: String): MutableList<WritingCvDataItem> {
        val user = UserLocal(application = app)
        return user.fillWriteFromLocal(username)
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