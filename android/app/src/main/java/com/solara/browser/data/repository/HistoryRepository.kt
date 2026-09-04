package com.solara.browser.data.repository

import com.solara.browser.data.local.HistoryDao
import com.solara.browser.data.model.HistoryEntity
import kotlinx.coroutines.flow.Flow

class HistoryRepository(private val historyDao: HistoryDao) {

    fun getRecentHistory(limit: Int = 100): Flow<List<HistoryEntity>> {
        return historyDao.getRecentHistory(limit)
    }

    fun searchHistory(query: String): Flow<List<HistoryEntity>> {
        return historyDao.searchHistory(query)
    }

    fun getHistoryByDateRange(start: Long, end: Long): Flow<List<HistoryEntity>> {
        return historyDao.getHistoryByDateRange(start, end)
    }

    suspend fun addEntry(title: String, url: String) {
        historyDao.insertHistoryEntry(
            HistoryEntity(
                title = title,
                url = url
            )
        )
    }

    suspend fun removeEntry(id: Long) {
        historyDao.deleteHistoryEntryById(id)
    }

    suspend fun clearAll() {
        historyDao.clearAllHistory()
    }

    suspend fun clearLastHour() {
        val oneHourAgo = System.currentTimeMillis() - 3_600_000
        historyDao.clearHistoryBefore(oneHourAgo)
    }

    suspend fun clearLastDay() {
        val oneDayAgo = System.currentTimeMillis() - 86_400_000
        historyDao.clearHistoryBefore(oneDayAgo)
    }

    suspend fun clearLastWeek() {
        val oneWeekAgo = System.currentTimeMillis() - 604_800_000
        historyDao.clearHistoryBefore(oneWeekAgo)
    }
}
