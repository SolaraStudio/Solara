package com.solara.browser.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabs")
data class TabEntity(
    @PrimaryKey val id: String,
    val workspaceId: String = "default",
    val title: String,
    val url: String,
    val faviconUrl: String? = null,
    val isActive: Boolean = false,
    val isIncognito: Boolean = false,
    val lastAccessed: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis()
)

data class TabInfo(
    val id: String,
    val title: String,
    val url: String,
    val faviconUrl: String? = null,
    val isActive: Boolean = false,
    val isIncognito: Boolean = false
) {
    companion object {
        fun fromEntity(entity: TabEntity) = TabInfo(
            id = entity.id,
            title = entity.title,
            url = entity.url,
            faviconUrl = entity.faviconUrl,
            isActive = entity.isActive,
            isIncognito = entity.isIncognito
        )
    }
}
