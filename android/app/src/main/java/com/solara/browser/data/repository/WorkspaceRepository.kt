package com.solara.browser.data.repository

import com.solara.browser.data.local.WorkspaceDao
import com.solara.browser.data.model.WorkspaceEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class WorkspaceRepository(private val workspaceDao: WorkspaceDao) {

    fun getAllWorkspaces(): Flow<List<WorkspaceEntity>> {
        return workspaceDao.getAllWorkspaces()
    }

    suspend fun getWorkspaceById(id: String): WorkspaceEntity? {
        return workspaceDao.getWorkspaceById(id)
    }

    suspend fun createWorkspace(
        name: String,
        icon: String = "language",
        color: Long = 0xFF8B5CF6
    ): WorkspaceEntity {
        val count = workspaceDao.getWorkspaceCount()
        val workspace = WorkspaceEntity(
            id = UUID.randomUUID().toString(),
            name = name,
            icon = icon,
            color = color,
            position = count
        )
        workspaceDao.insertWorkspace(workspace)
        return workspace
    }

    suspend fun updateWorkspace(workspace: WorkspaceEntity) {
        workspaceDao.updateWorkspace(workspace)
    }

    suspend fun deleteWorkspace(id: String) {
        workspaceDao.deleteWorkspaceById(id)
    }

    suspend fun ensureDefaultWorkspace() {
        val count = workspaceDao.getWorkspaceCount()
        if (count == 0) {
            workspaceDao.insertWorkspace(
                WorkspaceEntity(
                    id = "default",
                    name = "General",
                    icon = "language",
                    color = 0xFF8B5CF6,
                    position = 0
                )
            )
        }
    }
}
