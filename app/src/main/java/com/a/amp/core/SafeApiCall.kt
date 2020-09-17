package com.a.amp.core

import com.a.amp.core.resource.Resource
import retrofit2.Response

suspend inline fun <T> safeApiCall(responseFunction: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = responseFunction.invoke()
        if (response.isSuccessful)
            Resource.success(response.body(), response.code(), response.errorBody())
        else
            Resource.success(response.body(), response.code(), response.errorBody())
    } catch (e: Exception) {
        Resource.error(e.message.orEmpty(), null, -100)
    }
}