package com.a.amp.services

import com.a.amp.article.apimodel2.ArticleResponse
import com.a.amp.article.apimodel2.ArticleResponse2
import com.a.amp.user.apimodel1.Follow
import com.a.amp.user.apimodel1.LoginRequest
import com.a.amp.user.apimodel1.LoginResponse
import com.a.amp.user.apimodel1.followResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @POST("users/login")
    suspend fun login(@Body authRequest: LoginRequest): Response<LoginResponse>

    @POST("users")
    suspend fun register(@Body authRequest: LoginRequest): Response<LoginResponse>

    @GET("articles")
    suspend fun AllArticles(): Response<ArticleResponse>

    @GET("articles/{slug}")
    suspend fun getSingleArticleBySlug(
        @Path("slug")
        slug: String
    ): Response<ArticleResponse2>

    @GET("articles")
    suspend fun getArticlesByAuthor(
        @Query("author")
        username: String
    ): Response<ArticleResponse>

    @POST("profiles/{username}/follow")
    suspend fun Follow(@Path("username") username: String, @Body followRequest: Follow): Response<followResponse>

    @DELETE("/profiles/{USERNAME}/follow")
    suspend fun onFollow(@Path("username") username: String): Response<Unit>
}