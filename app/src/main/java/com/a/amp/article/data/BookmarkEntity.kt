package com.a.amp.article.data

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(
    tableName = "bookmarks", primaryKeys = ["Slg"]
)
data class BookmarkEntity(
    @ColumnInfo(name = "Slg") val slg: String
) {
    companion object {

    }
}