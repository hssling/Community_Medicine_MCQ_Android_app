package com.supernova.communitymedicine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supernova.communitymedicine.data.database.AppDatabase
import com.supernova.communitymedicine.data.model.Question
import com.supernova.communitymedicine.data.model.QuizResult
import com.supernova.communitymedicine.repository.QuestionRepository
import kotlinx.coroutines.launch

class ResultViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val questionRepository = QuestionRepository(database.questionDao())

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _quizResult = MutableLiveData<QuizResult?>()
    val quizResult: LiveData<QuizResult?> = _quizResult

    private val _chapterQuestions = MutableLiveData<List<Question>>()
    val chapterQuestions: LiveData<List<Question>> = _chapterQuestions

    init {
        _isLoading.value = false
        _errorMessage.value = null
    }

    fun loadQuizDetails(quizResult: QuizResult, chapter: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                _quizResult.value = quizResult

                // Load questions for the chapter to show detailed review
                val questions = questionRepository.getAllQuestionsByChapter(chapter)
                _chapterQuestions.value = questions

            } catch (e: Exception) {
                _errorMessage.value = "Error loading quiz details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getQuestionById(questionId: Long): Question? {
        return _chapterQuestions.value?.find { it.id == questionId }
    }

    fun calculateImprovementAreas(quizResult: QuizResult): List<String> {
        val areas = mutableListOf<String>()

        when {
            quizResult.percentage < 60 -> {
                areas.add("Focus on basic concepts")
                areas.add("Practice more questions daily")
                areas.add("Review explanations carefully")
            }
            quizResult.percentage < 80 -> {
                areas.add("Work on speed and accuracy")
                areas.add("Focus on weak topics")
                areas.add("Regular revision needed")
            }
            else -> {
                areas.add("Maintain consistency")
                areas.add("Focus on difficult topics")
            }
        }

        return areas
    }

    fun getPerformanceLevel(score: Float): String {
        return when {
            score >= 90 -> "Excellent"
            score >= 80 -> "Very Good"
            score >= 70 -> "Good"
            score >= 60 -> "Average"
            else -> "Needs Improvement"
        }
    }

    fun shouldShowCongratulations(score: Float): Boolean {
        return score >= 80
    }
}
