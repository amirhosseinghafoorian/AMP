package com.a.amp.article.data

import com.a.amp.article.apimodel2.*
import com.a.amp.core.resource.Resource
import com.a.amp.core.safeApiCall
import com.a.amp.services.AuthApi
import com.a.amp.services.RetrofitBuilder

class ArticleRemote {
    suspend fun getArticlesFromServer(): Resource<ArticleResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return allArticles(auth)
    }

    suspend fun getFeedFromServer(): Resource<ArticleResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return feed(auth)
    }

    suspend fun getSingleArticleBySlug(slug: String): Resource<ArticleResponse2> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return singleArticleBySlug(auth, slug)
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

    suspend fun editArticleForServer(
        body: String,
        description: String,
        tagList: List<String>,
        title: String,
        slug: String
    ): Resource<ArticleResponse4> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return editArticle(auth, body, description, tagList, title,slug)
    }

    suspend fun favoriteArticleForServer(
        slug: String
    ):Resource<ArticleResponse5>{
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return favoriteArticle(auth,slug)
    }

    suspend fun unFavoriteArticleForServer(
        slug: String
    ):Resource<ArticleResponse5>{
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return unFavoriteArticle(auth,slug)
    }


    suspend fun deleteArticleFormServer(
        id: String
    ): Resource<Unit> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return deleteArticle(auth, id)
    }

    suspend fun addCommentToServer(id: String, body: String): Resource<CommentResponse> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return addComment(auth, id, body)
    }

    suspend fun getSingleArticleCommentsFromServer(id: String): Resource<CommentResponse2> {
        val auth = RetrofitBuilder.retrofit.create(AuthApi::class.java)
        return singleArticleComments(auth, id)
    }

    private suspend fun singleArticleComments(
        authApi: AuthApi,
        id: String
    ): Resource<CommentResponse2> {
        return safeApiCall {
            authApi.getSingleArticleComments(id)
        }
    }

    private suspend fun deleteArticle(authApi: AuthApi, id: String): Resource<Unit> {
        return safeApiCall {
            authApi.deleteArticle(slug = id)
        }
    }

    private suspend fun allArticles(authApi: AuthApi): Resource<ArticleResponse> {
        return safeApiCall {
            authApi.allArticles()
        }
    }

    private suspend fun feed(authApi: AuthApi): Resource<ArticleResponse> {
        return safeApiCall {
            authApi.feed()
        }
    }

    private suspend fun addComment(
        authApi: AuthApi,
        id: String,
        body: String
    ): Resource<CommentResponse> {
        return safeApiCall {
            authApi.addComment(
                id,
                commentRequest = CommentRequest(
                    comment = Comment(body)
                )
            )
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


    private suspend fun editArticle(
        authApi: AuthApi,
        body: String,
        description: String,
        tagList: List<String>,
        title: String,
        slug: String
    ): Resource<ArticleResponse4> {
        return safeApiCall {
            authApi.editArticle(
                slug = slug,
                articleRequest = ArticleResponse4(
                    article = ArticleXXX(
                        body, description, tagList, title
                    )
                )
            )
        }
    }


    private suspend fun favoriteArticle(
        authApi: AuthApi,
        slug: String
    ): Resource<ArticleResponse5>{
        return safeApiCall {
            authApi.favoriteArticle(
                slug = slug
            )
        }
    }

    private suspend fun unFavoriteArticle(
        authApi: AuthApi,
        slug: String
    ): Resource<ArticleResponse5>{
        return safeApiCall {
            authApi.favoriteArticle(
                slug = slug
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