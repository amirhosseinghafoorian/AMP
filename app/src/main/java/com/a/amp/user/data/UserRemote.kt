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
    fun LoginFromServer() {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        login(auth)
    }

    fun login(authApi: AuthApi) {
        authApi.login(
            authRequest = LoginRequest(
                User(
                    email = "test@part.ir",
                    password = "11111111"
                )
            )
        ).enqueue(object : Callback<LoginResponse?> {
            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.i("baby", "failed baby")
            }

            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                Log.i("baby", response.body()?.user?.token.toString())
            }
        }
        )
    }
}