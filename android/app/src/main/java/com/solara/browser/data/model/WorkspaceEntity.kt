package com.solara.browser.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workspaces")
data class WorkspaceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val icon: String = "default",
    val color: Long = 0xFF8B5CF6,
    val position: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
