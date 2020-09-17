package com.a.amp.article.data

import com.a.amp.article.apimodel2.ArticleResponse
import com.a.amp.core.resource.Resource
import com.a.amp.core.safeApiCall
import com.a.amp.services.AuthApi
import com.a.amp.services.RetrofitBuilder

class ArticleRemote {
    suspend fun getArticlesFromServer(): Resource<ArticleResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return login(auth)
    }

    private suspend fun login(authApi: AuthApi): Resource<ArticleResponse> {

        return safeApiCall {
            authApi.AllArticles()
        }
    }
}