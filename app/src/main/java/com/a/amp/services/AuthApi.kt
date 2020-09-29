package com.a.amp.services

import com.a.amp.article.apimodel2.*
import com.a.amp.home.apimodel.TagModel
import com.a.amp.user.apimodel1.*
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {

    @POST("users/login")
    suspend fun login(@Body authRequest: LoginRequest): Response<LoginResponse>

    @POST("users")
    suspend fun register(@Body authRequest: LoginRequest): Response<LoginResponse>

    @GET("articles")
    suspend fun allArticles(): Response<ArticleResponse>

    @GET("articles/feed")
    suspend fun feed(): Response<ArticleResponse>

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

    @GET("articles/{slug}/comments")
    suspend fun getSingleArticleComments(
        @Path("slug") slug: String
    ): Response<CommentResponse2>

    @POST("articles")
    suspend fun createArticle(@Body articleRequest: ArticleResponse3): Response<ArticleResponse2>

    @GET("profiles/{username}")
    suspend fun getUser(@Path("username")username: String): Response<followResponse>

    @POST("articles/{slug}/comments")
    suspend fun addComment(
        @Path("slug") slug: String,
        @Body commentRequest: CommentRequest
    ): Response<CommentResponse>

    @POST("profiles/{username}/follow")
    suspend fun follow(
        @Path("username") username: String,
        @Body followRequest: Follow
    ): Response<followResponse>

    @DELETE("profiles/{username}/follow")
    suspend fun unFollow(@Path("username") username: String): Response<followResponse>

    @DELETE("articles/{slug}")
    suspend fun deleteArticle(@Path("slug") slug: String): Response<Unit>

    @PUT("articles/{slug}")
    suspend fun editArticle(
        @Path("slug") slug: String,
        @Body articleRequest: ArticleResponse4):Response<ArticleResponse4>

    @POST("articles/{slug}/favorite")
    suspend fun favoriteArticle(@Path("slug") slug: String): Response<ArticleResponse5>

    @DELETE("articles/{slug}/favorite")
    suspend fun unFavoriteArticle(@Path("slug") slug: String): Response<ArticleResponse5>

    @GET("tags")
    suspend fun getAllTags(): Response<TagModel>

    @GET("articles")
    suspend fun getArtFavByUsername(@Query("favorited") username: String): Response<ProfileArticleResponse>
}