package com.a.amp.home.data

import com.a.amp.core.resource.Resource
import com.a.amp.core.safeApiCall
import com.a.amp.home.apimodel.TagModel
import com.a.amp.services.AuthApi
import com.a.amp.services.RetrofitBuilder

class HomeRemote {

    suspend fun getTagFromServer(): Resource<TagModel> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return getTag(auth)
    }

    private suspend fun getTag(authApi: AuthApi): Resource<TagModel> {
        return safeApiCall {
            authApi.getAllTags()
        }
    }

}