package com.a.amp.article.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.a.amp.home.data.HomeRelatedCvDataItem
import com.a.amp.user.data.UserEntity
import com.a.amp.user.data.WritingCvDataItem
import java.util.*

@Entity(
    tableName = "articles", primaryKeys = ["ArticleId"], foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            parentColumns = ["UserId"],
            childColumns = ["UserOwnerId"],
            entity = UserEntity::class
        )
    ]
)
data class ArticleEntity(
    @ColumnInfo(name = "ArticleId") val articleId: Int,
    @ColumnInfo(name = "UserOwnerId") val userOwnerId: Int,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "MainText") val mainText: String?,
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
                        "user : ${list[i].userOwnerId}",
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

