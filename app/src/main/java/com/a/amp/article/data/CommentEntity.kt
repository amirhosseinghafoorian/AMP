package com.a.amp.article.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey.CASCADE
import com.a.amp.article.apimodel2.CommentXX

@Entity(
    tableName = "comments", primaryKeys = ["CommentId"], foreignKeys = [
        androidx.room.ForeignKey(
            onDelete = CASCADE,
            parentColumns = ["ArticleId"],
            childColumns = ["ConnectedArticleId"],
            entity = ArticleEntity::class
        )
    ]
)
data class CommentEntity(
    @ColumnInfo(name = "CommentId") val commentId: String,
    @ColumnInfo(name = "ConnectedArticleId") val connectedArticleId: String,
    @ColumnInfo(name = "Author") val author: String,
    @ColumnInfo(name = "Body") val body: String?
) {
    companion object {
        fun convertToDataItem(list: List<CommentEntity>): MutableList<CommentCvDataItem> {
            val resultList: MutableList<CommentCvDataItem> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    CommentCvDataItem(
                        list[i].body.toString(),
                        list[i].author,
                        list[i].commentId,
                        list[i].connectedArticleId
                    )
                )
            }
            return resultList
        }

        fun convertToDataItem2(list: List<CommentXX>, slug: String): MutableList<CommentEntity> {
            val resultList: MutableList<CommentEntity> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    CommentEntity(
                        list[i].id,
                        slug,
                        list[i].author.username,
                        list[i].body
                    )
                )
            }
            return resultList
        }
    }
}