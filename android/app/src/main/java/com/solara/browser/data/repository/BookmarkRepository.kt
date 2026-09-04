package com.solara.browser.data.repository

import com.solara.browser.data.local.BookmarkDao
import com.solara.browser.data.model.BookmarkEntity
import kotlinx.coroutines.flow.Flow

class BookmarkRepository(private val bookmarkDao: BookmarkDao) {

    fun getBookmarksByFolder(folderId: String): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getBookmarksByFolder(folderId)
    }

    fun getAllBookmarks(): Flow<List<BookmarkEntity>> {
        return bookmarkDao.getAllBookmarks()
    }

    fun searchBookmarks(query: String): Flow<List<BookmarkEntity>> {
        return bookmarkDao.searchBookmarks(query)
    }

    suspend fun isBookmarked(url: String): Boolean {
        return bookmarkDao.getBookmarkByUrl(url) != null
    }

    suspend fun addBookmark(title: String, url: String, folderId: String = "root"): Long {
        val existing = bookmarkDao.getBookmarkByUrl(url)
        if (existing != null) return existing.id
        return bookmarkDao.insertBookmark(
            BookmarkEntity(
                title = title,
                url = url,
                folderId = folderId
            )
        )
    }

    suspend fun removeBookmark(id: Long) {
        bookmarkDao.deleteBookmarkById(id)
    }

    suspend fun toggleBookmark(title: String, url: String): Boolean {
        val existing = bookmarkDao.getBookmarkByUrl(url)
        return if (existing != null) {
            bookmarkDao.deleteBookmark(existing)
            false
        } else {
            bookmarkDao.insertBookmark(
                BookmarkEntity(title = title, url = url)
            )
            true
        }
    }
}
