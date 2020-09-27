package com.a.amp.article.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.a.amp.article.apimodel2.Article
import com.a.amp.article.apimodel2.ArticleX
import com.a.amp.home.data.HomeRelatedCvDataItem
import com.a.amp.user.data.WritingCvDataItem
import java.util.*

@Entity(
    tableName = "articles", primaryKeys = ["ArticleId"]
)
data class ArticleEntity(
    @NonNull @ColumnInfo(name = "ArticleId") val articleId: String,
    @ColumnInfo(name = "UserOwnerId") val userOwnerId: String,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "MainText") val mainText: String?,
    @ColumnInfo(name = "IsFeed") val isFeed: Boolean = false,
    @ColumnInfo(name = "Favorited") val favorited: Boolean = false,
    @ColumnInfo(name = "FavoritesCount") val favoritesCount: Int = 0,
    @ColumnInfo(name = "Description") val description: String? = null,
    @ColumnInfo(name = "PublishDate") val publishDate: Date? = null
) {
    companion object {
        fun convertToDataItem1(list: List<ArticleEntity>): MutableList<ArticleRelatedCvDataItem> {
            val resultList: MutableList<ArticleRelatedCvDataItem> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    ArticleRelatedCvDataItem(
                        list[i].mainText.toString(),
                        "user : ${list[i].userOwnerId}",
                        list[i].publishDate.toString(),
                        list[i].articleId,
                        isTag = false
                    )
                )
            }
            return resultList
        }

        fun convertToDataItem2(list: List<ArticleEntity>): MutableList<WritingCvDataItem> {
            val resultList: MutableList<WritingCvDataItem> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    WritingCvDataItem(
                        list[i].title.toString(),
                        list[i].mainText.toString(),
                        list[i].userOwnerId,
                        list[i].publishDate.toString(),
                        list[i].articleId,
                        isTag = false,
                        isFav = false
                    )
                )
            }
            return resultList
        }

        fun convertToDataItem3(list: List<ArticleEntity>): MutableList<HomeRelatedCvDataItem> {
            val resultList: MutableList<HomeRelatedCvDataItem> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    HomeRelatedCvDataItem(
                        list[i].mainText.toString(),
                        "user : ${list[i].userOwnerId}",
                        list[i].publishDate.toString(),
                        list[i].articleId,
                        isTag = false
                    )
                )
            }
            return resultList
        }

        fun convertToDataItem4(list: List<Article>): MutableList<ArticleEntity> {
            val resultList: MutableList<ArticleEntity> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    ArticleEntity(
                        list[i].slug,
                        list[i].author.username,
                        list[i].title,
                        list[i].body,
                        favorited = list[i].favorited,
                        favoritesCount = list[i].favoritesCount,
                        description = list[i].description
                    )
                )
            }
            return resultList
        }

        fun convertToDataItem5(list: List<ArticleX>): MutableList<ArticleEntity> {
            val resultList: MutableList<ArticleEntity> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    ArticleEntity(
                        list[i].slug,
                        list[i].author.username, list[i].title, list[i].body
                    )
                )
            }
            return resultList
        }

        fun convertToDataItem6(list: List<Article>): MutableList<ArticleEntity> {
            val resultList: MutableList<ArticleEntity> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    ArticleEntity(
                        list[i].slug,
                        list[i].author.username,
                        list[i].title,
                        list[i].body,
                        true,
                        favorited = list[i].favorited,
                        favoritesCount = list[i].favoritesCount,
                        description = list[i].description
                    )
                )
            }
            return resultList
        }
    }
}

data class ArticleWithCommentsAndTags(
    @Embedded val article: ArticleEntity,
    @Relation(
        parentColumn = "ArticleId",
        entityColumn = "ConnectedArticleId"
    )
    val comments: List<CommentEntity>,
    @Relation(
        parentColumn = "ArticleId",
        entityColumn = "ConnectedArticleId"
    )
    val tags: List<TagEntity>
)

