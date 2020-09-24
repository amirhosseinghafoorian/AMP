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
    @ColumnInfo(name = "ConnectedArticleId") val connectedArticleId: String,
    @ColumnInfo(name = "Body") val body: String?,
    @ColumnInfo(name = "TagId") val tagId: String
) {
    companion object {
        fun convertToDataItem(list: List<String>, slug: String): MutableList<TagEntity> {
            val resultList: MutableList<TagEntity> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    TagEntity(slug, list[i], "$slug:${list[i]}")
                )
            }
            return resultList
        }
    }
}