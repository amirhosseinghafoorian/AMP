package com.a.amp.database

import androidx.room.*
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleWithCommentsAndTags
import com.a.amp.article.data.CommentEntity
import com.a.amp.article.data.TagEntity

@Dao
interface MyDao {

    @Query("select * from articles")
    suspend fun getArticles(): List<ArticleEntity>

    @Query("select * from articles where IsFeed == :myTrue")
    suspend fun getFeed(myTrue: Boolean): List<ArticleEntity>

    @Query("select * from articles where UserOwnerId == :username")
    suspend fun getArticlesByAuthor(username: String): List<ArticleEntity>

    @Query("select * from articles where ArticleId == :slug")
    suspend fun getSingleArticleById(slug: String): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(vararg articles: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(vararg tags: TagEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(vararg comments: CommentEntity)

    @Delete
    fun deleteArticles(vararg article: ArticleEntity)

    @Query("delete from articles where UserOwnerId == :username")
    suspend fun deleteArticlesByAuthor(username: String)

    @Transaction
    @Query("select * from articles where ArticleId == :slug")
    suspend fun getArticleWithCommentsAndTags(slug: String): List<ArticleWithCommentsAndTags>
}