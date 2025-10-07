package com.supernova.communitymedicine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supernova.communitymedicine.data.database.AppDatabase
import com.supernova.communitymedicine.data.model.ChapterProgress
import com.supernova.communitymedicine.repository.QuestionRepository
import com.supernova.communitymedicine.repository.QuizRepository
import kotlinx.coroutines.launch

class ChaptersViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val questionRepository = QuestionRepository(database.questionDao())
    private val quizRepository = QuizRepository(database.quizResultDao(), database.chapterProgressDao())

    private val _chapters = MutableLiveData<List<ChapterProgress>>()
    val chapters: LiveData<List<ChapterProgress>> = _chapters

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        _isLoading.value = false
        _errorMessage.value = null
    }

    fun loadChapters() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                // Get all chapter progress
                val chaptersProgress = quizRepository.getAllChapterProgress()

                // Update question counts for each chapter
                val updatedChapters = chaptersProgress.map { chapter ->
                    val questionCount = questionRepository.getQuestionCountByChapter(chapter.chapterName)
                    chapter.copy(totalQuestions = questionCount)
                }

                _chapters.value = updatedChapters

            } catch (e: Exception) {
                _errorMessage.value = "Error loading chapters: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshChapters() {
        loadChapters()
    }

    fun getChapterProgress(chapterName: String): ChapterProgress? {
        return _chapters.value?.find { it.chapterName == chapterName }
    }

    fun getCompletedChaptersCount(): Int {
        return _chapters.value?.count { it.isCompleted } ?: 0
    }

    fun getOverallProgress(): Float {
        val chapters = _chapters.value ?: return 0f
        return if (chapters.isNotEmpty()) {
            chapters.sumOf { it.questionsAttempted } / chapters.sumOf { it.totalQuestions }.toFloat() * 100
        } else {
            0f
        }
    }
}
