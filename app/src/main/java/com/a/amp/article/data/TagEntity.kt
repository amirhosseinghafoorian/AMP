package com.a.amp.article.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "tags", primaryKeys = ["TagId"], foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            parentColumns = ["ArticleId"],
            childColumns = ["ConnectedArticleId"],
            entity = ArticleEntity::class
        )
    ]
)
data class TagEntity(
    @ColumnInfo(name = "TagId") val tagId: Int,
    @ColumnInfo(name = "ConnectedArticleId") val connectedArticleId: Int,
    @ColumnInfo(name = "TagName") val tagName: String?
)