package com.solara.browser.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solara.browser.data.model.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentHistory(limit: Int = 100): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM history WHERE timestamp BETWEEN :start AND :end ORDER BY timestamp DESC")
    fun getHistoryByDateRange(start: Long, end: Long): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM history WHERE title LIKE '%' || :query || '%' OR url LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchHistory(query: String): Flow<List<HistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryEntry(entry: HistoryEntity)

    @Delete
    suspend fun deleteHistoryEntry(entry: HistoryEntity)

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun deleteHistoryEntryById(id: Long)

    @Query("DELETE FROM history")
    suspend fun clearAllHistory()

    @Query("DELETE FROM history WHERE timestamp < :before")
    suspend fun clearHistoryBefore(before: Long)

    @Query("SELECT COUNT(*) FROM history")
    suspend fun getHistoryCount(): Int
}
