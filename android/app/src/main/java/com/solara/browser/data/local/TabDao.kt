package com.solara.browser.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.solara.browser.data.model.TabEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TabDao {

    @Query("SELECT * FROM tabs WHERE workspaceId = :workspaceId ORDER BY lastAccessed DESC")
    fun getTabsByWorkspace(workspaceId: String): Flow<List<TabEntity>>

    @Query("SELECT * FROM tabs WHERE isActive = 1 AND workspaceId = :workspaceId LIMIT 1")
    suspend fun getActiveTab(workspaceId: String): TabEntity?

    @Query("SELECT * FROM tabs WHERE id = :tabId")
    suspend fun getTabById(tabId: String): TabEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTab(tab: TabEntity)

    @Update
    suspend fun updateTab(tab: TabEntity)

    @Delete
    suspend fun deleteTab(tab: TabEntity)

    @Query("DELETE FROM tabs WHERE id = :tabId")
    suspend fun deleteTabById(tabId: String)

    @Query("UPDATE tabs SET isActive = 0 WHERE workspaceId = :workspaceId")
    suspend fun deactivateAllTabs(workspaceId: String)

    @Query("SELECT COUNT(*) FROM tabs WHERE workspaceId = :workspaceId")
    suspend fun getTabCount(workspaceId: String): Int

    @Query("DELETE FROM tabs WHERE workspaceId = :workspaceId AND isActive = 0 AND lastAccessed < :cutoffTime")
    suspend fun closeInactiveTabs(workspaceId: String, cutoffTime: Long)

    @Query("SELECT * FROM tabs WHERE isIncognito = 1 ORDER BY lastAccessed DESC")
    fun getIncognitoTabs(): Flow<List<TabEntity>>

    @Query("DELETE FROM tabs WHERE isIncognito = 1")
    suspend fun clearIncognitoTabs()
}
