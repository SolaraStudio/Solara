package com.solara.browser.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val url: String,
    val faviconUrl: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
