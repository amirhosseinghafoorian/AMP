package com.a.amp.user.data

import com.a.amp.core.SafeApiCall
import com.a.amp.core.resource.Resource
import com.a.amp.services.AuthApi
import com.a.amp.services.RetrofitBuilder
import com.a.amp.user.apimodel1.LoginRequest
import com.a.amp.user.apimodel1.LoginResponse
import com.a.amp.user.apimodel1.User

class UserRemote {
    suspend fun loginFromServer(user: String, pass: String): Resource<LoginResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return login(auth, user, pass)
    }

    suspend fun registerInServer(user: String, pass: String, email: String): Boolean {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return signUp(auth, user, pass, email)
    }

    suspend fun login(authApi: AuthApi, user: String, pass: String): Resource<LoginResponse> {
        val apiResult: Resource<LoginResponse>

        apiResult = SafeApiCall {
            authApi.login(
                authRequest = LoginRequest(
                    User(
                        email = user,
                        password = pass
                    )
                )//test@part.ir , 11111111
            )
        }

        return apiResult
    }

    suspend fun signUp(authApi: AuthApi, username: String, pass: String, email: String): Boolean {
        var result = false
        val a = authApi.register(
            authRequest = LoginRequest(
                User(
                    email = email,
                    password = pass,
                    username = username
                )
            )//test@part.ir , 11111111
        )
//        if (a.code() == 200) {
//            result = true
//        }

        return result
    }

}