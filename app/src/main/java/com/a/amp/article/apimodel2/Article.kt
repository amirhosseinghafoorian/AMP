package com.a.amp.article.apimodel2

data class Article(
    val author: AuthorX,
    val body: String,
    val createdAt: String,
    val description: String,
    val favorited: Boolean,
    val favoritesCount: Int,
    val slug: String,
    val tagList: List<String>,
    val title: String,
    val updatedAt: String
)