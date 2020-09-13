package com.a.amp.user.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.ArticleWithCommentsAndTags

@Entity(tableName = "users", primaryKeys = ["UserId"])
data class UserEntity(
    @ColumnInfo(name = "UserId") val userId: Int,
    @ColumnInfo(name = "UserName") val userName: String?,
    @ColumnInfo(name = "Count") var count: Int? = null
)

data class UserWithArticles(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "UserId",
        entityColumn = "UserOwnerId"
    )
    val articles: List<ArticleEntity>
)

data class UserWithArticlesWithCommentsAndTags(
    @Embedded val user: UserEntity,
    @Relation(
        entity = ArticleEntity::class,
        parentColumn = "UserId",
        entityColumn = "UserOwnerId"
    )
    val articles: List<ArticleWithCommentsAndTags>
)