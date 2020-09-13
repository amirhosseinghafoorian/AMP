package com.a.amp.article.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.a.amp.user.data.UserEntity
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

