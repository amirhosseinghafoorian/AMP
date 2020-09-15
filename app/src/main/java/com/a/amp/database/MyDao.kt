package com.a.amp.database

import androidx.room.*
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleWithCommentsAndTags
import com.a.amp.article.data.CommentEntity
import com.a.amp.article.data.TagEntity
import com.a.amp.user.data.UserEntity
import com.a.amp.user.data.UserWithArticles
import com.a.amp.user.data.UserWithArticlesWithCommentsAndTags

@Dao
interface MyDao {

    @Query("select * from articles")
    fun getArticles(): List<ArticleEntity>

    @Query("select * from users")
    fun getUsers(): List<UserEntity>

    @Query("select * from comments")
    fun getComments(): List<CommentEntity>

    @Query("select * from tags")
    fun getTags(): List<TagEntity>

    @Query("select *, count(article.title) as count from users as user  join articles as article on user.userId = article.UserOwnerId group by user.UserName")
    fun UserWithArticleCount(): List<UserEntity>

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

    @Delete
    fun deleteComments(vararg comment: CommentEntity)

    @Delete
    fun deleteTags(vararg tag: TagEntity)

    @Delete
    fun deleteUsers(vararg user: UserEntity)

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