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

    suspend fun followOtherUser(
        username: String,
        email: String
    ):Resource<followResponse>{
        val followuser = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return following(followuser, username, email)
    }

    suspend fun following(
        authApi: AuthApi,
        username: String,
        email: String
    ): Resource<followResponse>{
        return safeApiCall {
            authApi.Follow(username = username,
                followRequest = Follow(
                    User(
                        email = email,

                    )
                )
            )
        }
    }suspend fun onFollowOtherUser(username: String):Resource<Unit>{
        val onfollowuser = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return onFollowing(onfollowuser,username)
    }

    suspend fun onFollowing(
        authApi: AuthApi,
        username: String
    ): Resource<Unit>{
        return safeApiCall {
            authApi.onFollow(username = username)
        }
    }


    suspend fun getArticlesByAuthorFromServer(username: String): Resource<ArticleResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return ArticlesByAuthor(auth, username)
    }

    private suspend fun ArticlesByAuthor(
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
                )//test@part.ir , 11111111
            )
        }
    }

}