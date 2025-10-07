package com.supernova.communitymedicine.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.supernova.communitymedicine.data.model.Bookmark

@Dao
interface BookmarksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarks(bookmarks: List<Bookmark>)

    @Update
    suspend fun updateBookmark(bookmark: Bookmark)

    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks WHERE id = :bookmarkId")
    suspend fun getBookmarkById(bookmarkId: String): Bookmark?

    @Query("SELECT * FROM bookmarks ORDER BY dateBookmarked DESC")
    suspend fun getAllBookmarks(): List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE chapter = :chapter ORDER BY dateBookmarked DESC")
    suspend fun getBookmarksByChapter(chapter: String): List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE difficulty = :difficulty ORDER BY dateBookmarked DESC")
    suspend fun getBookmarksByDifficulty(difficulty: String): List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE tag = :tag ORDER BY dateBookmarked DESC")
    suspend fun getBookmarksByTag(tag: String): List<Bookmark>

    @Query("SELECT DISTINCT tag FROM bookmarks WHERE tag IS NOT NULL AND tag <> '' ORDER BY tag ASC")
    suspend fun getAllTags(): List<String>

    @Query("SELECT * FROM bookmarks WHERE questionId = :questionId LIMIT 1")
    suspend fun getBookmarkByQuestionId(questionId: Long): Bookmark?

    @Query("SELECT COUNT(*) FROM bookmarks")
    suspend fun getBookmarksCount(): Int

    @Query("SELECT COUNT(*) FROM bookmarks WHERE chapter = :chapter")
    suspend fun getBookmarksCountByChapter(chapter: String): Int

    @Query("SELECT * FROM bookmarks ORDER BY lastReviewed DESC LIMIT :limit")
    suspend fun getRecentBookmarks(limit: Int = 10): List<Bookmark>

    @Query("SELECT * FROM bookmarks ORDER BY reviewCount DESC LIMIT :limit")
    suspend fun getMostReviewedBookmarks(limit: Int = 10): List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE dateBookmarked >= :since ORDER BY dateBookmarked DESC")
    suspend fun getBookmarksAddedSince(since: Long): List<Bookmark>

    @Query("SELECT * FROM bookmarks WHERE lastReviewed >= :since ORDER BY lastReviewed DESC")
    suspend fun getBookmarksReviewedSince(since: Long): List<Bookmark>

    @Query("SELECT COUNT(*) FROM bookmarks WHERE lastReviewed >= :since")
    suspend fun getReviewCountSince(since: Long): Int

    @Query("DELETE FROM bookmarks WHERE id = :bookmarkId")
    suspend fun deleteBookmarkById(bookmarkId: String)

    @Query("DELETE FROM bookmarks WHERE questionId = :questionId")
    suspend fun deleteBookmarksByQuestionId(questionId: Long)

    @Query("DELETE FROM bookmarks")
    suspend fun deleteAllBookmarks()
}
