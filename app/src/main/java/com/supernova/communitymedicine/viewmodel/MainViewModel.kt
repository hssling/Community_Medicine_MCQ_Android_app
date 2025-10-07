package com.supernova.communitymedicine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supernova.communitymedicine.data.database.AppDatabase
import com.supernova.communitymedicine.data.model.ChapterProgress
import com.supernova.communitymedicine.data.model.OverallProgress
import com.supernova.communitymedicine.repository.QuestionRepository
import com.supernova.communitymedicine.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val questionRepository = QuestionRepository(database.questionDao())
    private val quizRepository = QuizRepository(database.quizResultDao(), database.chapterProgressDao())

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _overallProgress = MutableSharedFlow<OverallProgress>()
    val overallProgress: SharedFlow<OverallProgress> = _overallProgress

    init {
        _isLoading.value = false
        _errorMessage.value = null
    }

    fun loadInitialData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                // Load sample data if database is empty
                loadSampleDataIfNeeded()

                // Calculate overall progress
                calculateOverallProgress()

            } catch (e: Exception) {
                _errorMessage.value = "Error loading data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun loadSampleDataIfNeeded() {
        val chapters = questionRepository.getAllChapters()
        if (chapters.isEmpty()) {
            // Load sample MCQ data
            loadSampleMCQs()
        }
    }

    private suspend fun loadSampleMCQs() {
        // This will be implemented when we create the data seeding
        // For now, we'll create a basic set of community medicine questions
        val sampleQuestions = createSampleQuestions()
        questionRepository.insertQuestions(sampleQuestions)

        // Initialize chapter progress
        initializeChapterProgress()
    }

    private fun createSampleQuestions(): List<com.supernova.communitymedicine.data.model.Question> {
        // Sample community medicine questions for NEET preparation
        return listOf(
            com.supernova.communitymedicine.data.model.Question(
                questionText = "What is the primary health care approach mainly concerned with?",
                optionA = "Treatment of diseases",
                optionB = "Prevention and promotion of health",
                optionC = "Hospital management",
                optionD = "Specialized medical care",
                correctAnswer = "B",
                explanation = "Primary health care focuses on prevention, promotion, and basic health services accessible to all.",
                chapter = "Concept of Health and Disease",
                difficulty = "Easy"
            ),
            com.supernova.communitymedicine.data.model.Question(
                questionText = "Which of the following is NOT a component of primary health care?",
                optionA = "Health education",
                optionB = "Maternal and child health",
                optionC = "Tertiary care services",
                optionD = "Immunization",
                correctAnswer = "C",
                explanation = "Tertiary care is specialized care, not part of primary health care which focuses on basic and preventive services.",
                chapter = "Concept of Health and Disease",
                difficulty = "Easy"
            ),
            com.supernova.communitymedicine.data.model.Question(
                questionText = "The Alma-Ata Declaration was adopted in which year?",
                optionA = "1978",
                optionB = "1980",
                optionC = "1985",
                optionD = "1990",
                correctAnswer = "A",
                explanation = "The Alma-Ata Declaration on Primary Health Care was adopted in 1978 by WHO and UNICEF.",
                chapter = "Health Care Delivery System",
                difficulty = "Medium"
            ),
            com.supernova.communitymedicine.data.model.Question(
                questionText = "What is the main objective of the National Health Policy 2017?",
                optionA = "Universal health coverage",
                optionB = "Eradication of communicable diseases",
                optionC = "Improvement of medical education",
                optionD = "Development of health infrastructure",
                correctAnswer = "A",
                explanation = "The National Health Policy 2017 aims to achieve universal health coverage and deliver quality health care services.",
                chapter = "Health Care Delivery System",
                difficulty = "Medium"
            ),
            com.supernova.communitymedicine.data.model.Question(
                questionText = "Which vaccine is given at birth under the National Immunization Schedule?",
                optionA = "BCG",
                optionB = "OPV",
                optionC = "Hepatitis B",
                optionD = "Both A and C",
                correctAnswer = "D",
                explanation = "Both BCG (for tuberculosis) and Hepatitis B vaccines are given at birth under the National Immunization Schedule.",
                chapter = "Immunization",
                difficulty = "Easy"
            )
        )
    }

    private suspend fun initializeChapterProgress() {
        val chapters = listOf(
            "Concept of Health and Disease",
            "Health Care Delivery System",
            "Immunization",
            "Communicable Diseases",
            "Non-Communicable Diseases",
            "Demography and Family Planning",
            "Nutrition and Health",
            "Environmental Health",
            "Occupational Health",
            "Health Education"
        )

        chapters.forEach { chapter ->
            val existing = quizRepository.getChapterProgress(chapter)
            if (existing == null) {
                val progress = ChapterProgress(
                    chapterName = chapter,
                    totalQuestions = 10, // Will be updated based on actual count
                    questionsAttempted = 0,
                    questionsCorrect = 0,
                    questionsWrong = 0,
                    questionsSkipped = 0
                )
                quizRepository.insertChapterProgress(progress)
            }
        }
    }

    private suspend fun calculateOverallProgress() {
        try {
            val allProgress = quizRepository.getAllChapterProgress()
            val totalChapters = allProgress.size
            val completedChapters = allProgress.count { it.isCompleted }

            val totalQuestions = allProgress.sumOf { it.totalQuestions }
            val attemptedQuestions = allProgress.sumOf { it.questionsAttempted }
            val correctAnswers = allProgress.sumOf { it.questionsCorrect }

            val completionPercentage = if (totalChapters > 0) {
                (completedChapters.toFloat() / totalChapters.toFloat()) * 100
            } else 0f

            val progress = OverallProgress(
                totalQuestions = totalQuestions,
                attemptedQuestions = attemptedQuestions,
                correctAnswers = correctAnswers,
                completionPercentage = completionPercentage,
                currentStreak = 0 // Will be calculated based on daily activity
            )

            _overallProgress.emit(progress)
        } catch (e: Exception) {
            _errorMessage.value = "Error calculating progress: ${e.message}"
        }
    }

    fun getOverallProgress(): Flow<OverallProgress> = _overallProgress
}
