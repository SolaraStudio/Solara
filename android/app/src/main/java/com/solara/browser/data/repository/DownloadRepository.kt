package com.solara.browser.data.repository

import com.solara.browser.data.local.DownloadDao
import com.solara.browser.data.model.DownloadEntity
import com.solara.browser.data.model.DownloadStatus
import kotlinx.coroutines.flow.Flow

class DownloadRepository(private val downloadDao: DownloadDao) {

    fun getAllDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getAllDownloads()
    }

    fun getActiveDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getActiveDownloads()
    }

    suspend fun startDownload(fileName: String, url: String, mimeType: String?): Long {
        return downloadDao.insertDownload(
            DownloadEntity(
                fileName = fileName,
                url = url,
                mimeType = mimeType,
                status = DownloadStatus.PENDING
            )
        )
    }

    suspend fun updateProgress(downloadId: Long, downloadedBytes: Long, totalBytes: Long) {
        val download = downloadDao.getDownloadById(downloadId) ?: return
        downloadDao.updateDownload(
            download.copy(
                downloadedBytes = downloadedBytes,
                totalBytes = totalBytes,
                status = DownloadStatus.DOWNLOADING
            )
        )
    }

    suspend fun completeDownload(downloadId: Long, filePath: String) {
        val download = downloadDao.getDownloadById(downloadId) ?: return
        downloadDao.updateDownload(
            download.copy(
                filePath = filePath,
                status = DownloadStatus.COMPLETED,
                completedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun failDownload(downloadId: Long) {
        val download = downloadDao.getDownloadById(downloadId) ?: return
        downloadDao.updateDownload(download.copy(status = DownloadStatus.FAILED))
    }

    suspend fun cancelDownload(downloadId: Long) {
        val download = downloadDao.getDownloadById(downloadId) ?: return
        downloadDao.updateDownload(download.copy(status = DownloadStatus.CANCELLED))
    }

    suspend fun removeDownload(id: Long) {
        downloadDao.deleteDownloadById(id)
    }

    suspend fun clearCompleted() {
        downloadDao.clearCompletedDownloads()
    }
}
