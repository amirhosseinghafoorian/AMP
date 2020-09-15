package com.a.amp.services

import com.a.amp.user.apimodel1.LoginRequest
import com.a.amp.user.apimodel1.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("users/login")
    suspend fun login(@Body authRequest: LoginRequest): Response<LoginResponse>

    @POST("users")
    suspend fun register(@Body authRequest: LoginRequest): Response<LoginResponse>
}