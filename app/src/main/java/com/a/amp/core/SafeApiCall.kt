package com.a.amp.core

import com.a.amp.core.resource.Resource
import retrofit2.Response

suspend inline fun <T> SafeApiCall(responseFunction: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = responseFunction.invoke()
        if (response.isSuccessful)
            Resource.success(response.body(), response.code())
        else
            Resource.success(response.body(), response.code())
    } catch (e: Exception) {
        Resource.error(e.message.orEmpty(), null, -100)
    }
}