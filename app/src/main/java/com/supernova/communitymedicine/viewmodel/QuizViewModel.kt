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
import com.supernova.communitymedicine.repository.QuizRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val questionRepository = QuestionRepository(database.questionDao())
    private val quizRepository = QuizRepository(database.quizResultDao(), database.chapterProgressDao())

    private val _currentQuestion = MutableLiveData<Question?>()
    val currentQuestion: LiveData<Question?> = _currentQuestion

    private val _currentQuestionIndex = MutableLiveData<Int>()
    val currentQuestionIndex: LiveData<Int> = _currentQuestionIndex

    private val _selectedAnswer = MutableLiveData<String?>()
    val selectedAnswer: LiveData<String?> = _selectedAnswer

    private val _isLastQuestion = MutableLiveData<Boolean>()
    val isLastQuestion: LiveData<Boolean> = _isLastQuestion

    private val _quizCompleted = MutableLiveData<Boolean>()
    val quizCompleted: LiveData<Boolean> = _quizCompleted

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _timeRemaining = MutableLiveData<Long>()
    val timeRemaining: LiveData<Long> = _timeRemaining

    // Quiz data
    private lateinit var questions: List<Question>
    private val questionResults = mutableMapOf<Long, Pair<String, Boolean>>() // questionId -> (selectedAnswer, isCorrect)
    private var quizStartTime: Long = 0
    private var totalTimeSpent: Long = 0

    var chapter: String = ""
    var difficulty: String = ""
    var totalQuestions: Int = 0

    init {
        _currentQuestionIndex.value = 0
        _selectedAnswer.value = null
        _isLastQuestion.value = false
        _quizCompleted.value = false
        _isLoading.value = false
        _errorMessage.value = null
        _timeRemaining.value = 0
    }

    fun startQuiz(chapter: String, difficulty: String, questionCount: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                this@QuizViewModel.chapter = chapter
                this@QuizViewModel.difficulty = difficulty
                this@QuizViewModel.totalQuestions = questionCount

                // Load questions based on chapter and difficulty
                questions = if (difficulty == "Mixed") {
                    questionRepository.getRandomQuestionsByChapter(chapter, questionCount)
                } else {
                    questionRepository.getRandomQuestionsByChapterAndDifficulty(chapter, difficulty, questionCount)
                }

                if (questions.isEmpty()) {
                    _errorMessage.value = "No questions available for this chapter"
                    return@launch
                }

                // Initialize quiz
                quizStartTime = System.currentTimeMillis()
                questionResults.clear()

                // Show first question
                showQuestion(0)

            } catch (e: Exception) {
                _errorMessage.value = "Error starting quiz: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun showQuestion(index: Int) {
        if (index < 0 || index >= questions.size) return

        _currentQuestionIndex.value = index
        _currentQuestion.value = questions[index]
        _selectedAnswer.value = null
        _isLastQuestion.value = (index == questions.size - 1)
        _timeRemaining.value = questions[index].timeLimit * 1000L
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun clearSelection() {
        _selectedAnswer.value = null
    }

    fun submitAnswer() {
        val currentQuestion = _currentQuestion.value ?: return
        val selectedAnswer = _selectedAnswer.value

        if (selectedAnswer.isNullOrEmpty()) return

        // Record the answer
        val isCorrect = selectedAnswer == currentQuestion.correctAnswer
        questionResults[currentQuestion.id] = Pair(selectedAnswer, isCorrect)

        // Update question statistics in database
        viewModelScope.launch {
            val timestamp = System.currentTimeMillis()
            if (isCorrect) {
                questionRepository.markQuestionCorrect(currentQuestion.id, timestamp)
            } else {
                questionRepository.markQuestionIncorrect(currentQuestion.id, timestamp)
            }
        }

        // Move to next question or complete quiz
        if (_isLastQuestion.value == true) {
            completeQuiz()
        } else {
            nextQuestion()
        }
    }

    fun skipQuestion() {
        val currentQuestion = _currentQuestion.value ?: return

        // Record as skipped (empty answer)
        questionResults[currentQuestion.id] = Pair("", false)

        // Move to next question or complete quiz
        if (_isLastQuestion.value == true) {
            completeQuiz()
        } else {
            nextQuestion()
        }
    }

    fun nextQuestion() {
        val currentIndex = _currentQuestionIndex.value ?: 0
        if (currentIndex < questions.size - 1) {
            showQuestion(currentIndex + 1)
        }
    }

    fun previousQuestion() {
        val currentIndex = _currentQuestionIndex.value ?: 0
        if (currentIndex > 0) {
            showQuestion(currentIndex - 1)
        }
    }

    fun toggleBookmark() {
        val currentQuestion = _currentQuestion.value ?: return

        viewModelScope.launch {
            val newBookmarkStatus = !currentQuestion.isBookmarked
            questionRepository.updateBookmarkStatus(currentQuestion.id, newBookmarkStatus)

            // Update the current question with new bookmark status
            _currentQuestion.value = currentQuestion.copy(isBookmarked = newBookmarkStatus)
        }
    }

    fun updateTimeRemaining(timeInMillis: Long) {
        _timeRemaining.value = timeInMillis
    }

    private fun completeQuiz() {
        viewModelScope.launch {
            try {
                // Calculate quiz results
                val endTime = System.currentTimeMillis()
                totalTimeSpent = endTime - quizStartTime

                val correctAnswers = questionResults.count { it.value.second }
                val wrongAnswers = questionResults.count { !it.value.second && it.value.first.isNotEmpty() }
                val skippedQuestions = questionResults.count { it.value.first.isEmpty() }

                val score = (correctAnswers.toFloat() / questions.size.toFloat()) * 100

                // Create quiz result
                val quizResult = QuizResult(
                    dateTime = endTime,
                    chapter = chapter,
                    totalQuestions = questions.size,
                    correctAnswers = correctAnswers,
                    wrongAnswers = wrongAnswers,
                    skippedQuestions = skippedQuestions,
                    timeTaken = TimeUnit.MILLISECONDS.toSeconds(totalTimeSpent),
                    score = score,
                    percentage = score,
                    difficulty = difficulty,
                    questionsAnswered = questionResults.keys.toList(),
                    correctQuestionIds = questionResults.filter { it.value.second }.keys.toList(),
                    wrongQuestionIds = questionResults.filter { !it.value.second && it.value.first.isNotEmpty() }.keys.toList(),
                    skippedQuestionIds = questionResults.filter { it.value.first.isEmpty() }.keys.toList()
                )

                // Save quiz result and update progress
                quizRepository.saveQuizResultAndUpdateProgress(quizResult, questionResults.mapValues { it.value.second })

                _quizCompleted.value = true

            } catch (e: Exception) {
                _errorMessage.value = "Error completing quiz: ${e.message}"
            }
        }
    }

    fun getQuizResults(): QuizResult? {
        if (questionResults.isEmpty()) return null

        val correctAnswers = questionResults.count { it.value.second }
        val wrongAnswers = questionResults.count { !it.value.second && it.value.first.isNotEmpty() }
        val skippedQuestions = questionResults.count { it.value.first.isEmpty() }

        val score = (correctAnswers.toFloat() / questions.size.toFloat()) * 100

        return QuizResult(
            dateTime = System.currentTimeMillis(),
            chapter = chapter,
            totalQuestions = questions.size,
            correctAnswers = correctAnswers,
            wrongAnswers = wrongAnswers,
            skippedQuestions = skippedQuestions,
            timeTaken = TimeUnit.MILLISECONDS.toSeconds(totalTimeSpent),
            score = score,
            percentage = score,
            difficulty = difficulty,
            questionsAnswered = questionResults.keys.toList(),
            correctQuestionIds = questionResults.filter { it.value.second }.keys.toList(),
            wrongQuestionIds = questionResults.filter { !it.value.second && it.value.first.isNotEmpty() }.keys.toList(),
            skippedQuestionIds = questionResults.filter { it.value.first.isEmpty() }.keys.toList()
        )
    }
}
