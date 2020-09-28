package com.a.amp.user.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.a.amp.article.data.ArticleEntity

@Entity(
    tableName = "UserFav", primaryKeys = ["Id"], foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["ArticleId"],
            childColumns = ["ConnectedArticleId"],
            entity = ArticleEntity::class
        )
    ]
)
data class UserFavEntity(
    @ColumnInfo(name = "ConnectedArticleId") val connectedArticleId: String,
    @ColumnInfo(name = "UserName") val username: String?,
    @ColumnInfo(name = "Id") val id: String
) {
    companion object {
        fun convertToDataItem(
            slugList: List<String>,
            username: String
        ): MutableList<UserFavEntity> {
            val resultList: MutableList<UserFavEntity> = mutableListOf()
            for (i in slugList.indices) {
                resultList.add(
                    UserFavEntity(slugList[i], username, "$username:${slugList[i]}")
                )
            }
            return resultList
        }
    }
}