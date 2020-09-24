package com.a.amp.database

import androidx.room.*
import com.a.amp.article.data.*
import com.a.amp.user.data.UserEntity

@Dao
interface MyDao {

    @Query("select * from articles")
    suspend fun getArticles(): List<ArticleEntity>

    @Query("select * from articles where UserOwnerId == :username")
    suspend fun getArticlesByAuthor(username: String): List<ArticleEntity>

    @Query("select * from articles where ArticleId == :slug")
    suspend fun getSingleArticleById(slug: String): List<ArticleEntity>

    @Query("select * from users")
    fun getUsers(): List<UserEntity>

    @Query("select *, count(article.title) as count from users as user  join articles as article on user.userId = article.UserOwnerId group by user.UserName")
    fun userWithArticleCount(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(vararg articles: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg users: UserEntity)

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
    suspend fun getSingleArticleWithComments(slug: String): List<ArticleWithComments>

    @Transaction
    @Query("select * from articles where ArticleId == :slug")
    suspend fun getArticleWithCommentsAndTags(slug: String): List<ArticleWithCommentsAndTags>
}