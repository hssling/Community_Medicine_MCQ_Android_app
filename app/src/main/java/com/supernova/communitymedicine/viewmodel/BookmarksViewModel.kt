package com.supernova.communitymedicine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supernova.communitymedicine.data.database.AppDatabase
import com.supernova.communitymedicine.data.model.Bookmark
import com.supernova.communitymedicine.repository.BookmarksRepository
import com.supernova.communitymedicine.repository.QuestionRepository
import kotlinx.coroutines.launch

class BookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val bookmarksRepository = BookmarksRepository(database.bookmarksDao())
    private val questionRepository = QuestionRepository(database.questionDao())

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _bookmarks = MutableLiveData<List<Bookmark>>()
    val bookmarks: LiveData<List<Bookmark>> = _bookmarks

    private val _filteredBookmarks = MutableLiveData<List<Bookmark>>()
    val filteredBookmarks: LiveData<List<Bookmark>> = _filteredBookmarks

    init {
        _isLoading.value = false
        _errorMessage.value = null
    }

    fun loadBookmarks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val bookmarksList = bookmarksRepository.getAllBookmarks()
                _bookmarks.value = bookmarksList
                _filteredBookmarks.value = bookmarksList

            } catch (e: Exception) {
                _errorMessage.value = "Error loading bookmarks: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addBookmark(questionId: Long, chapter: String, difficulty: String, note: String? = null, tag: String? = null) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val question = questionRepository.getQuestionById(questionId)

                if (question != null) {
                    val bookmark = Bookmark(
                        id = generateBookmarkId(),
                        questionId = questionId,
                        questionText = question.questionText,
                        questionOptions = question.getOptions(),
                        correctAnswer = question.correctAnswer,
                        chapter = chapter,
                        difficulty = difficulty,
                        note = note,
                        tag = tag,
                        dateBookmarked = System.currentTimeMillis(),
                        lastReviewed = null,
                        reviewCount = 0
                    )

                    bookmarksRepository.insertBookmark(bookmark)
                    loadBookmarks() // Refresh the list
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error adding bookmark: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                bookmarksRepository.removeBookmark(bookmark)
                loadBookmarks() // Refresh the list
            } catch (e: Exception) {
                _errorMessage.value = "Error removing bookmark: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                bookmarksRepository.updateBookmark(bookmark)
                loadBookmarks() // Refresh the list
            } catch (e: Exception) {
                _errorMessage.value = "Error updating bookmark: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateBookmarkNote(bookmark: Bookmark, note: String) {
        val updatedBookmark = bookmark.copy(note = note.ifEmpty { null })
        updateBookmark(updatedBookmark)
    }

    fun markBookmarkReviewed(bookmark: Bookmark) {
        val updatedBookmark = bookmark.copy(
            lastReviewed = System.currentTimeMillis(),
            reviewCount = bookmark.reviewCount + 1
        )
        updateBookmark(updatedBookmark)
    }

    fun filterBookmarks(difficulty: String? = null, tag: String? = null) {
        val allBookmarks = _bookmarks.value ?: return

        val filtered = allBookmarks.filter { bookmark ->
            val difficultyMatch = difficulty == null || bookmark.difficulty.lowercase() == difficulty
            val tagMatch = tag == null || bookmark.tag == tag

            difficultyMatch && tagMatch
        }

        _filteredBookmarks.value = filtered
    }

    fun searchBookmarks(query: String) {
        val allBookmarks = _bookmarks.value ?: return

        if (query.isBlank()) {
            _filteredBookmarks.value = allBookmarks
            return
        }

        val lowercaseQuery = query.lowercase()
        val filtered = allBookmarks.filter { bookmark ->
            bookmark.questionText.lowercase().contains(lowercaseQuery) ||
            bookmark.chapter.lowercase().contains(lowercaseQuery) ||
            bookmark.difficulty.lowercase().contains(lowercaseQuery) ||
            bookmark.note?.lowercase()?.contains(lowercaseQuery) == true ||
            bookmark.tag?.lowercase()?.contains(lowercaseQuery) == true
        }

        _filteredBookmarks.value = filtered
    }

    private fun generateBookmarkId(): String {
        return "bookmark_${System.currentTimeMillis()}_${(0..9999).random()}"
    }

    fun getBookmarkById(bookmarkId: String): Bookmark? {
        return _bookmarks.value?.find { it.id == bookmarkId }
    }

    fun getBookmarksByChapter(chapter: String): List<Bookmark> {
        return _bookmarks.value?.filter { it.chapter == chapter } ?: emptyList()
    }

    fun getRecentBookmarks(limit: Int = 10): List<Bookmark> {
        return _bookmarks.value?.sortedByDescending { it.dateBookmarked }?.take(limit) ?: emptyList()
    }

    fun getMostReviewedBookmarks(limit: Int = 10): List<Bookmark> {
        return _bookmarks.value?.sortedByDescending { it.reviewCount }?.take(limit) ?: emptyList()
    }
}
