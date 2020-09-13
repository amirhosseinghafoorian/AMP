package com.a.amp.article.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "comments", primaryKeys = ["CommentId"], foreignKeys = [
        androidx.room.ForeignKey(
            onDelete = androidx.room.ForeignKey.CASCADE,
            parentColumns = ["ArticleId"],
            childColumns = ["ConnectedArticleId"],
            entity = ArticleEntity::class
        )
    ]
)
data class CommentEntity(
    @ColumnInfo(name = "CommentId") val commentId: Int,
    @ColumnInfo(name = "ConnectedArticleId") val connectedArticleId: Int,
    @ColumnInfo(name = "CommentText") val commentText: String?
) {
    companion object {
        fun convertToDataItem(cms: List<CommentEntity>): MutableList<CommentCvDataItem> {
            val resultList: MutableList<CommentCvDataItem> = mutableListOf()
            for (i in cms.indices) {
                resultList.add(CommentCvDataItem(cms[i].commentText!!, "Test $i", cms[i].commentId))
            }
            return resultList
        }
    }
}