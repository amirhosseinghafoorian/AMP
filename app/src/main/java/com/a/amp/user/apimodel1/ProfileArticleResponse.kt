package com.a.amp.user.apimodel1
import com.a.amp.article.apimodel2.Article

data class ProfileArticleResponse(
    val articles: List<Article>,
    val articlesCount: Int
)