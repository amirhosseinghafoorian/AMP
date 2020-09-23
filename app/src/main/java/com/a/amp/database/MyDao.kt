package com.a.amp.database

import androidx.room.*
import com.a.amp.article.data.*
import com.a.amp.user.data.UserEntity
import com.a.amp.user.data.UserWithArticles
import com.a.amp.user.data.UserWithArticlesWithCommentsAndTags

@Dao
interface MyDao {

    @Query("select * from articles")
    suspend fun getArticles(): List<ArticleEntity>

    @Query("select * from articles where UserOwnerId == :username")
    suspend fun getArticlesByAuthor(username: String): List<ArticleEntity>

    @Query("select * from articles where ArticleId == :id")
    suspend fun getSingleArticleById(id: String): List<ArticleEntity>

    @Transaction
    @Query("select * from articles where ArticleId == :id")
    suspend fun getSingleArticleWithComments(id: String): List<ArticleWithComments>

    @Query("select * from users")
    fun getUsers(): List<UserEntity>

    @Query("select * from comments where ConnectedArticleId == :slug")
    suspend fun getComments(slug: String): List<CommentEntity>

    @Query("select * from tags")
    fun getTags(): List<TagEntity>

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

    @Delete
    fun deleteComments(vararg comment: CommentEntity)

    @Delete
    fun deleteTags(vararg tag: TagEntity)

    @Transaction
    @Query("select * from articles")
    fun getArticleWithCommentsAndTags(): List<ArticleWithCommentsAndTags>

    @Transaction
    @Query("select * from users")
    fun getUserWithArticles(): List<UserWithArticles>

    @Transaction
    @Query("select * from users")
    fun getUserWithArticlesWithCommentsAndTags(): List<UserWithArticlesWithCommentsAndTags>
}