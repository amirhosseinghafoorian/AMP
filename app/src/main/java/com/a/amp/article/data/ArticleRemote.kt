package com.a.amp.article.data

import com.a.amp.article.apimodel2.ArticleResponse
import com.a.amp.article.apimodel2.ArticleResponse2
import com.a.amp.article.apimodel2.ArticleResponse3
import com.a.amp.article.apimodel2.ArticleXX
import com.a.amp.core.resource.Resource
import com.a.amp.core.safeApiCall
import com.a.amp.services.AuthApi
import com.a.amp.services.RetrofitBuilder
import retrofit2.Response
import retrofit2.create

class ArticleRemote {
    suspend fun getArticlesFromServer(): Resource<ArticleResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return allArticles(auth)
    }
    //TODO put article by author in repo

    suspend fun getSingleArticleBySlug(id: String): Resource<ArticleResponse2> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return singleArticleBySlug(auth, id)
    }

    suspend fun createArticleForServer(
        body: String,
        description: String,
        tagList: List<String>,
        title: String
    ): Resource<ArticleResponse2> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return createArticle(auth, body, description, tagList, title)
    }

    suspend fun deleteArticleFormServer(
        id: String
    ): Resource<Unit>{
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return deleteArticle(auth,id)
    }


    private suspend fun deleteArticle(authApi: AuthApi, id: String): Resource<Unit>{
        return safeApiCall {
            authApi.deleteArticle(slug = id)
        }
    }

    private suspend fun allArticles(authApi: AuthApi): Resource<ArticleResponse> {
        return safeApiCall {
            authApi.allArticles()
        }
    }

    private suspend fun createArticle(
        authApi: AuthApi,
        body: String,
        description: String,
        tagList: List<String>,
        title: String
    ): Resource<ArticleResponse2> {
        return safeApiCall {
            authApi.createArticle(
                articleRequest = ArticleResponse3(
                    article = ArticleXX(
                        body, description, tagList, title
                    )
                )
            )
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