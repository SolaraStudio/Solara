package com.solara.browser.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val url: String,
    val faviconUrl: String? = null,
    val folderId: String = "root",
    val position: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
