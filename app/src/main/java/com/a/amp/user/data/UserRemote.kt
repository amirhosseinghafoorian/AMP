package com.a.amp.user.data

import android.util.Log
import com.a.amp.AuthApi
import com.a.amp.core.RetrofitBuilder
import com.a.amp.user.apimodel1.LoginRequest
import com.a.amp.user.apimodel1.LoginResponse
import com.a.amp.user.apimodel1.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemote {
    suspend fun loginFromServer(user: String, pass: String): Boolean {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return login(auth, user, pass)
    }

    suspend fun registerInServer(user: String, pass: String): Boolean {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return login(auth, user, pass)
    }

    suspend fun login(authApi: AuthApi, user: String, pass: String): Boolean {
        var result = false
        authApi.login(
            authRequest = LoginRequest(
                User(
                    email = user,
                    password = pass
                )
            )//test@part.ir , 11111111
        ).enqueue(object : Callback<LoginResponse?> {
            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.i("baby", "failed baby")
                result = false
            }

            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                Log.i("baby", response.body()?.user?.email.toString())
                result = true
            }
        }
        )
        return result
    }

}