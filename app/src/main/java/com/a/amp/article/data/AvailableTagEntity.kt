package com.a.amp.article.data

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(
    tableName = "availableTags", primaryKeys = ["TagBody"]
)
data class AvailableTagEntity(
    @ColumnInfo(name = "TagBody") val tagBody: String
) {
    companion object {
        fun convertToDataItem(list: List<AvailableTagEntity>): MutableList<String> {
            val resultList: MutableList<String> = mutableListOf()
            for (i in list.indices) {
                resultList.add(
                    list[i].tagBody
                )
            }
            return resultList
        }
    }
}