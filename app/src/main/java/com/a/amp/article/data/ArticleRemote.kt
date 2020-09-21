package com.a.amp.article.data

import com.a.amp.article.apimodel2.ArticleResponse
import com.a.amp.article.apimodel2.ArticleResponse2
import com.a.amp.core.resource.Resource
import com.a.amp.core.safeApiCall
import com.a.amp.services.AuthApi
import com.a.amp.services.RetrofitBuilder

class ArticleRemote {
    suspend fun getArticlesFromServer(): Resource<ArticleResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return allArticles(auth)
    }

    suspend fun getSingleArticleBySlug(id: String): Resource<ArticleResponse2> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return singleArticleBySlug(auth, id)
    }

    private suspend fun allArticles(authApi: AuthApi): Resource<ArticleResponse> {
        return safeApiCall {
            authApi.AllArticles()
        }
    }

    private suspend fun singleArticleBySlug(
        authApi: AuthApi,
        slug: String
    ): Resource<ArticleResponse2> {
        return safeApiCall {
            authApi.getSingleArticleBySlug(slug)
        }
    }

}