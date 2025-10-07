package com.supernova.communitymedicine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.supernova.communitymedicine.data.database.AppDatabase
import com.supernova.communitymedicine.data.model.Achievement
import com.supernova.communitymedicine.data.model.ChapterStatistics
import com.supernova.communitymedicine.data.model.QuizResult
import com.supernova.communitymedicine.data.model.Statistics
import com.supernova.communitymedicine.repository.QuestionRepository
import com.supernova.communitymedicine.repository.QuizRepository
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val questionRepository = QuestionRepository(database.questionDao())
    private val quizRepository = QuizRepository(database.quizResultDao(), database.chapterProgressDao())

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _statistics = MutableLiveData<Statistics>()
    val statistics: LiveData<Statistics> = _statistics

    private val _chapterStats = MutableLiveData<List<ChapterStatistics>>()
    val chapterStats: LiveData<List<ChapterStatistics>> = _chapterStats

    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> = _achievements

    private val _quizHistory = MutableLiveData<List<QuizResult>>()
    val quizHistory: LiveData<List<QuizResult>> = _quizHistory

    init {
        _isLoading.value = false
        _errorMessage.value = null
    }

    fun loadStatistics() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                // Load all data concurrently
                val allChapters = quizRepository.getAllChapterProgress()
                val allQuizResults = quizRepository.getAllQuizResults()
                val allQuestions = questionRepository.getAllChapters()

                // Calculate overall statistics
                val overallStats = calculateOverallStatistics(allChapters, allQuizResults)
                _statistics.value = overallStats

                // Calculate chapter-specific statistics
                val chapterStats = calculateChapterStatistics(allChapters, allQuizResults)
                _chapterStats.value = chapterStats

                // Generate achievements
                val achievements = generateAchievements(overallStats, chapterStats, allQuizResults)
                _achievements.value = achievements

                // Load recent quiz history
                val recentHistory = allQuizResults.take(10) // Last 10 quizzes
                _quizHistory.value = recentHistory

            } catch (e: Exception) {
                _errorMessage.value = "Error loading statistics: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun calculateOverallStatistics(
        chapters: List<com.supernova.communitymedicine.data.model.ChapterProgress>,
        quizResults: List<QuizResult>
    ): Statistics {
        val totalQuestions = chapters.sumOf { it.totalQuestions }
        val attemptedQuestions = chapters.sumOf { it.questionsAttempted }
        val correctAnswers = chapters.sumOf { it.questionsCorrect }
        val completedChapters = chapters.count { it.isCompleted }
        val totalChapters = chapters.size

        val overallProgress = if (totalQuestions > 0) {
            (attemptedQuestions.toFloat() / totalQuestions.toFloat()) * 100
        } else 0f

        val overallAccuracy = if (attemptedQuestions > 0) {
            (correctAnswers.toFloat() / attemptedQuestions.toFloat()) * 100
        } else 0f

        val totalQuizzes = quizResults.size
        val averageScore = if (totalQuizzes > 0) {
            quizResults.map { it.percentage }.average().toFloat()
        } else 0f

        val totalStudyTime = chapters.sumOf { it.totalTimeSpent }
        val maxStreak = chapters.maxOfOrNull { it.bestStreak } ?: 0
        val currentStreak = chapters.maxOfOrNull { it.currentStreak } ?: 0

        // Calculate achievements
        val totalAchievements = 20 // Total possible achievements
        val unlockedAchievements = calculateUnlockedAchievements(chapters, quizResults)

        // Recent activity (last 7 days)
        val weekAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
        val weeklyQuizzes = quizResults.count { it.dateTime >= weekAgo }
        val weeklyProgress = if (totalQuizzes > 0) (weeklyQuizzes.toFloat() / totalQuizzes.toFloat()) * 100 else 0f

        // Monthly activity (last 30 days)
        val monthAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(30)
        val monthlyQuizzes = quizResults.count { it.dateTime >= monthAgo }
        val monthlyProgress = if (totalQuizzes > 0) (monthlyQuizzes.toFloat() / totalQuizzes.toFloat()) * 100 else 0f

        return Statistics(
            overallProgress = overallProgress,
            totalQuestions = totalQuestions,
            overallAccuracy = overallAccuracy,
            currentStreak = currentStreak,
            bestStreak = maxStreak,
            quizzesCompleted = totalQuizzes,
            averageScore = averageScore,
            totalStudyTime = totalStudyTime,
            chaptersCompleted = completedChapters,
            totalChapters = totalChapters,
            totalAchievements = totalAchievements,
            unlockedAchievements = unlockedAchievements,
            lastQuizDate = quizResults.maxOfOrNull { it.dateTime },
            weeklyProgress = weeklyProgress,
            monthlyProgress = monthlyProgress
        )
    }

    private fun calculateChapterStatistics(
        chapters: List<com.supernova.communitymedicine.data.model.ChapterProgress>,
        quizResults: List<QuizResult>
    ): List<ChapterStatistics> {
        return chapters.map { chapter ->
            val chapterQuizzes = quizResults.filter { it.chapter == chapter.chapterName }
            val recentScores = chapterQuizzes.takeLast(5).map { it.percentage }

            // Calculate improvement trend
            val improvementTrend = if (recentScores.size >= 2) {
                val recentAvg = recentScores.takeLast(2).average().toFloat()
                val earlierAvg = recentScores.dropLast(2).average().toFloat()
                recentAvg - earlierAvg
            } else 0f

            ChapterStatistics(
                chapterName = chapter.chapterName,
                totalQuestions = chapter.totalQuestions,
                questionsAttempted = chapter.questionsAttempted,
                questionsCorrect = chapter.questionsCorrect,
                bestScore = chapter.bestScore,
                averageScore = chapter.averageScore,
                quizzesCompleted = chapter.quizzesCompleted,
                totalTimeSpent = chapter.totalTimeSpent,
                isCompleted = chapter.isCompleted,
                lastAccessed = chapter.lastAccessed,
                currentStreak = chapter.currentStreak,
                bestStreak = chapter.bestStreak,
                masteryLevel = chapter.getMasteryLevel(),
                easyCorrect = chapter.easyQuestionsCorrect,
                mediumCorrect = chapter.mediumQuestionsCorrect,
                hardCorrect = chapter.hardQuestionsCorrect,
                recentScores = recentScores,
                improvementTrend = improvementTrend
            )
        }
    }

    private fun generateAchievements(
        overallStats: Statistics,
        chapterStats: List<ChapterStatistics>,
        quizResults: List<QuizResult>
    ): List<Achievement> {
        val achievements = mutableListOf<Achievement>()

        // Quiz completion achievements
        achievements.add(Achievement(
            id = "first_quiz",
            title = "First Steps",
            description = "Complete your first quiz",
            iconResId = R.drawable.ic_check,
            badgeColor = R.color.progress_good,
            isUnlocked = overallStats.quizzesCompleted >= 1,
            unlockedDate = quizResults.minOfOrNull { it.dateTime },
            progress = minOf(overallStats.quizzesCompleted.toFloat(), 1f),
            maxProgress = 1f,
            category = Achievement.AchievementCategory.QUIZ,
            rarity = Achievement.AchievementRarity.COMMON
        ))

        // Streak achievements
        achievements.add(Achievement(
            id = "week_streak",
            title = "Week Warrior",
            description = "Maintain a 7-day study streak",
            iconResId = R.drawable.ic_stats,
            badgeColor = R.color.streak_active,
            isUnlocked = overallStats.bestStreak >= 7,
            unlockedDate = null, // Would need to track when streak was achieved
            progress = minOf(overallStats.bestStreak.toFloat(), 7f),
            maxProgress = 7f,
            category = Achievement.AchievementCategory.STREAK,
            rarity = Achievement.AchievementRarity.RARE
        ))

        // Accuracy achievements
        achievements.add(Achievement(
            id = "perfect_score",
            title = "Perfectionist",
            description = "Achieve a perfect score (100%)",
            iconResId = R.drawable.ic_stats,
            badgeColor = R.color.progress_excellent,
            isUnlocked = quizResults.any { it.percentage >= 100f },
            unlockedDate = quizResults.find { it.percentage >= 100f }?.dateTime,
            progress = if (quizResults.any { it.percentage >= 100f }) 100f else 0f,
            maxProgress = 100f,
            category = Achievement.AchievementCategory.ACCURACY,
            rarity = Achievement.AchievementRarity.EPIC
        ))

        // Chapter completion achievements
        achievements.add(Achievement(
            id = "chapter_master",
            title = "Chapter Master",
            description = "Complete all chapters",
            iconResId = R.drawable.ic_bookmark,
            badgeColor = R.color.progress_excellent,
            isUnlocked = overallStats.chaptersCompleted >= overallStats.totalChapters,
            unlockedDate = null,
            progress = overallStats.chaptersCompleted.toFloat(),
            maxProgress = overallStats.totalChapters.toFloat(),
            category = Achievement.AchievementCategory.CHAPTER,
            rarity = Achievement.AchievementRarity.LEGENDARY
        ))

        return achievements
    }

    private fun calculateUnlockedAchievements(
        chapters: List<com.supernova.communitymedicine.data.model.ChapterProgress>,
        quizResults: List<QuizResult>
    ): Int {
        var unlockedCount = 0

        // First quiz achievement
        if (quizResults.isNotEmpty()) unlockedCount++

        // Streak achievements
        if (chapters.any { it.bestStreak >= 7 }) unlockedCount++

        // Perfect score achievement
        if (quizResults.any { it.percentage >= 100f }) unlockedCount++

        // Chapter completion achievement
        if (chapters.all { it.isCompleted }) unlockedCount++

        return unlockedCount
    }
}
