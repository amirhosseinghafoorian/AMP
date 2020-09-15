package com.a.amp.services

import com.a.amp.MyApp
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(FlipperOkhttpInterceptor(MyApp.networkFlipperPlugin))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.5.69:3000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}