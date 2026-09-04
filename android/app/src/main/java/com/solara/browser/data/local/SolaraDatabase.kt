package com.solara.browser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.solara.browser.data.model.BookmarkEntity
import com.solara.browser.data.model.DownloadEntity
import com.solara.browser.data.model.HistoryEntity
import com.solara.browser.data.model.TabEntity
import com.solara.browser.data.model.WorkspaceEntity

@Database(
    entities = [
        TabEntity::class,
        WorkspaceEntity::class,
        BookmarkEntity::class,
        HistoryEntity::class,
        DownloadEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class SolaraDatabase : RoomDatabase() {

    abstract fun tabDao(): TabDao
    abstract fun workspaceDao(): WorkspaceDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun historyDao(): HistoryDao
    abstract fun downloadDao(): DownloadDao

    companion object {
        @Volatile
        private var INSTANCE: SolaraDatabase? = null

        fun getInstance(context: Context): SolaraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SolaraDatabase::class.java,
                    "solara_browser.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
