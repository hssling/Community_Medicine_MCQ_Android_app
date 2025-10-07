package com.supernova.communitymedicine.repository

import com.supernova.communitymedicine.data.dao.BookmarksDao
import com.supernova.communitymedicine.data.model.Bookmark

class BookmarksRepository(private val bookmarksDao: BookmarksDao) {

    suspend fun insertBookmark(bookmark: Bookmark) {
        bookmarksDao.insertBookmark(bookmark)
    }

    suspend fun insertBookmarks(bookmarks: List<Bookmark>) {
        bookmarksDao.insertBookmarks(bookmarks)
    }

    suspend fun updateBookmark(bookmark: Bookmark) {
        bookmarksDao.updateBookmark(bookmark)
    }

    suspend fun removeBookmark(bookmark: Bookmark) {
        bookmarksDao.deleteBookmark(bookmark)
    }

    suspend fun getBookmarkById(bookmarkId: String): Bookmark? {
        return bookmarksDao.getBookmarkById(bookmarkId)
    }

    suspend fun getAllBookmarks(): List<Bookmark> {
        return bookmarksDao.getAllBookmarks()
    }

    suspend fun getBookmarksByChapter(chapter: String): List<Bookmark> {
        return bookmarksDao.getBookmarksByChapter(chapter)
    }

    suspend fun getBookmarksByDifficulty(difficulty: String): List<Bookmark> {
        return bookmarksDao.getBookmarksByDifficulty(difficulty)
    }

    suspend fun getBookmarksByTag(tag: String): List<Bookmark> {
        return bookmarksDao.getBookmarksByTag(tag)
    }

    suspend fun getAllTags(): List<String> {
        return bookmarksDao.getAllTags()
    }

    suspend fun getBookmarkByQuestionId(questionId: Long): Bookmark? {
        return bookmarksDao.getBookmarkByQuestionId(questionId)
    }

    suspend fun getBookmarksCount(): Int {
        return bookmarksDao.getBookmarksCount()
    }

    suspend fun getBookmarksCountByChapter(chapter: String): Int {
        return bookmarksDao.getBookmarksCountByChapter(chapter)
    }

    suspend fun getRecentBookmarks(limit: Int = 10): List<Bookmark> {
        return bookmarksDao.getRecentBookmarks(limit)
    }

    suspend fun getMostReviewedBookmarks(limit: Int = 10): List<Bookmark> {
        return bookmarksDao.getMostReviewedBookmarks(limit)
    }

    suspend fun getBookmarksAddedSince(since: Long): List<Bookmark> {
        return bookmarksDao.getBookmarksAddedSince(since)
    }

    suspend fun getBookmarksReviewedSince(since: Long): List<Bookmark> {
        return bookmarksDao.getBookmarksReviewedSince(since)
    }

    suspend fun getReviewCountSince(since: Long): Int {
        return bookmarksDao.getReviewCountSince(since)
    }

    suspend fun removeBookmarkById(bookmarkId: String) {
        bookmarksDao.deleteBookmarkById(bookmarkId)
    }

    suspend fun removeBookmarksByQuestionId(questionId: Long) {
        bookmarksDao.deleteBookmarksByQuestionId(questionId)
    }

    suspend fun clearAllBookmarks() {
        bookmarksDao.deleteAllBookmarks()
    }

    // Utility methods for bookmark management
    suspend fun isQuestionBookmarked(questionId: Long): Boolean {
        return getBookmarkByQuestionId(questionId) != null
    }

    suspend fun toggleBookmark(questionId: Long, chapter: String, difficulty: String): Bookmark? {
        val existingBookmark = getBookmarkByQuestionId(questionId)

        return if (existingBookmark != null) {
            // Remove bookmark if it exists
            removeBookmark(existingBookmark)
            null
        } else {
            // Create new bookmark with minimal info
            // The full bookmark creation happens in the ViewModel
            null // This should be handled by the ViewModel for complete bookmark creation
        }
    }

    suspend fun getBookmarkStats(): Map<String, Int> {
        val allBookmarks = getAllBookmarks()

        return mapOf(
            "total" to allBookmarks.size,
            "reviewCount" to allBookmarks.sumOf { it.reviewCount },
            "uniqueChapters" to allBookmarks.distinctBy { it.chapter }.size,
            "uniqueTags" to allBookmarks.distinctBy { it.tag }.count { it.tag != null }
        )
    }

    suspend fun getBookmarksByReviewFrequency(minReviews: Int): List<Bookmark> {
        return getAllBookmarks().filter { it.reviewCount >= minReviews }
    }

    suspend fun getUnreviewedBookmarks(): List<Bookmark> {
        return getAllBookmarks().filter { it.lastReviewed == null }
    }

    suspend fun getRecentlyAddedBookmarks(days: Int): List<Bookmark> {
        val cutoffTime = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L)
        return getBookmarksAddedSince(cutoffTime)
    }

    suspend fun exportBookmarks(): String {
        val bookmarks = getAllBookmarks()
        return bookmarks.joinToString("\n") { bookmark ->
            """
                Bookmark ID: ${bookmark.id}
                Question: ${bookmark.questionText}
                Chapter: ${bookmark.chapter}
                Difficulty: ${bookmark.difficulty}
                Correct Answer: ${bookmark.correctAnswer}
                Tag: ${bookmark.tag ?: "None"}
                Note: ${bookmark.note ?: "None"}
                Bookmarked: ${java.util.Date(bookmark.dateBookmarked)}
                Last Reviewed: ${bookmark.lastReviewed?.let { java.util.Date(it) } ?: "Never"}
                Review Count: ${bookmark.reviewCount}

                ---
            """.trimIndent()
        }
    }
}
