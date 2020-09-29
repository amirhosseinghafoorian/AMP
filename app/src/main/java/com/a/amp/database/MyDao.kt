package com.a.amp.database

import androidx.room.*
import com.a.amp.article.data.*
import com.a.amp.user.data.UserFavEntity
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleWithCommentsAndTags
import com.a.amp.article.data.CommentEntity
import com.a.amp.article.data.TagEntity

@Dao
interface MyDao {

    @Query("select * from articles")
    suspend fun getArticles(): List<ArticleEntity>

    @Query("select * from articles where IsFeed == :myTrue and Title like :text ")
    suspend fun getFeed(myTrue: Boolean, text: String): List<ArticleEntity>

    @Query("select * from articles where UserOwnerId == :username")
    suspend fun getArticlesByAuthor(username: String): List<ArticleEntity>

    @Query("select * from articles where Favorited == :myTrue")
    suspend fun getLikedArticles(myTrue: Boolean): List<ArticleEntity>

    @Query("select * from articles order by FavoritesCount desc limit 10")
    suspend fun getTopArticles(): List<ArticleEntity>

    @Query("select * from articles inner join tags as tag on ArticleId == ConnectedArticleId where tag.Body == :myTag")
    suspend fun getArticlesInTag(myTag: String): List<ArticleEntity>

    @Query("select * from bookmarks where Slg == :slug")
    suspend fun getBookmark(slug: String): List<BookmarkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(vararg articles: ArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(vararg tags: TagEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(vararg comments: CommentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFavs(vararg userFav: UserFavEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarks(vararg bookmark: BookmarkEntity)

    @Query("delete from bookmarks where Slg == :slug")
    suspend fun deleteBookmark(slug: String)

    @Delete
    fun deleteArticles(vararg article: ArticleEntity)

    @Query("delete from articles where UserOwnerId == :username")
    suspend fun deleteArticlesByAuthor(username: String)

    @Transaction
    @Query("select * from articles where ArticleId == :slug")
    suspend fun getArticleWithCommentsAndTags(slug: String): List<ArticleWithCommentsAndTags>
}