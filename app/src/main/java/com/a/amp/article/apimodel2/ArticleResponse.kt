package com.a.amp.article.apimodel2

data class ArticleResponse(
    val articles: List<Article>,
    val articlesCount: Int
)