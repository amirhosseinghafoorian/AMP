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
            articleList: List<ArticleEntity>,
            username: String
        ): MutableList<UserFavEntity> {
            val resultList: MutableList<UserFavEntity> = mutableListOf()
            for (i in articleList.indices) {
                resultList.add(
                    UserFavEntity(
                        articleList[i].articleId,
                        username,
                        "$username:${articleList[i].articleId}"
                    )
                )
            }
            return resultList
        }
    }
}