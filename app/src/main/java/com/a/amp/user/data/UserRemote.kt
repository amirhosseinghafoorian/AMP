package com.a.amp.user.data

import com.a.amp.article.apimodel2.ArticleResponse
import com.a.amp.core.resource.Resource
import com.a.amp.core.safeApiCall
import com.a.amp.services.AuthApi
import com.a.amp.services.RetrofitBuilder
import com.a.amp.user.apimodel1.*

class UserRemote {
    suspend fun loginFromServer(user: String, pass: String): Resource<LoginResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return login(auth, user, pass)
    }

    suspend fun registerInServer(
        user: String,
        pass: String,
        email: String
    ): Resource<LoginResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return signUp(auth, user, pass, email)
    }


    suspend fun getUserServer(username: String): Resource<followResponse>{
        val  auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return getProfile(auth,username)
    }

    suspend fun getArtFavByUsername(username: String): Resource<ProfileArticleResponse>{
        val  auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return artFavUsername(auth,username)
    }

    suspend fun followOtherUser(
        username: String,
        email: String
    ):Resource<followResponse>{
        val followUser = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return following(followUser, username, email)
    }

    suspend fun unFollowOtherUser(username: String):Resource<followResponse>{
        val unFollowUser = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return unFollowUser(unFollowUser,username)
    }

    suspend fun getArticlesByAuthorFromServer(username: String): Resource<ArticleResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return articlesByAuthor(auth, username)
    }

    private suspend fun articlesByAuthor(
        authApi: AuthApi,
        username: String
    ): Resource<ArticleResponse> {
        return safeApiCall {
            authApi.getArticlesByAuthor(username)
        }
    }

    private suspend fun login(
        authApi: AuthApi,
        user: String,
        pass: String
    ): Resource<LoginResponse> {

        return safeApiCall {
            authApi.login(
                authRequest = LoginRequest(
                    User(
                        email = user,
                        password = pass
                    )
                )
            )
        }
    }

    private suspend fun signUp(
        authApi: AuthApi,
        username: String,
        pass: String,
        email: String
    ): Resource<LoginResponse> {

        return safeApiCall {
            authApi.register(
                authRequest = LoginRequest(
                    User(
                        email = email,
                        password = pass,
                        username = username
                    )
                )
            )
        }
    }


    private suspend fun getProfile(
        authApi: AuthApi,
        username: String
    ): Resource<followResponse>{
        return safeApiCall {
            authApi.getUser(username)
        }
    }

    private suspend fun following(
        authApi: AuthApi,
        username: String,
        email: String
    ): Resource<followResponse> {
        return safeApiCall {
            authApi.follow(
                username = username,
                followRequest = Follow(
                    User(
                        email = email,

                        )
                )
            )
        }
    }

    private suspend fun unFollowUser(
        authApi: AuthApi,
        username: String
    ): Resource<followResponse> {
        return safeApiCall {
            authApi.unFollow(username = username)
        }
    }

    suspend fun artFavUsername(
        authApi: AuthApi,
        username: String
    ): Resource<ProfileArticleResponse> {
        return safeApiCall {
            authApi.getArtFavByUsername(username)
        }
    }


}