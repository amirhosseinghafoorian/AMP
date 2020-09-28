package com.a.amp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a.amp.article.data.ArticleEntity
import com.a.amp.article.data.CommentEntity
import com.a.amp.article.data.TagEntity
import com.a.amp.user.data.UserFavEntity

@Database(
    entities = [ArticleEntity::class, CommentEntity::class, TagEntity::class, UserFavEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun myDao(): MyDao

    companion object {

        private const val databaseName = "amp-db"

        fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, databaseName)
                .build()
        }
    }

}