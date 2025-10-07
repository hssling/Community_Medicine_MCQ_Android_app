package com.supernova.communitymedicine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapter_progress")
data class ChapterProgress(
    @PrimaryKey
    val chapterName: String,

    val totalQuestions: Int,
    val questionsAttempted: Int,
    val questionsCorrect: Int,
    val questionsWrong: Int,
    val questionsSkipped: Int,

    // Progress tracking
    val bestScore: Float = 0f,
    val averageScore: Float = 0f,
    val totalTimeSpent: Long = 0, // in seconds
    val quizzesCompleted: Int = 0,

    // Completion status
    val isCompleted: Boolean = false,
    val completionPercentage: Float = 0f,
    val lastAccessed: Long = System.currentTimeMillis(),

    // Difficulty breakdown
    val easyQuestionsCorrect: Int = 0,
    val mediumQuestionsCorrect: Int = 0,
    val hardQuestionsCorrect: Int = 0,

    // Streak tracking
    val currentStreak: Int = 0,
    val bestStreak: Int = 0
) {
    fun getAccuracy(): Float {
        return if (questionsAttempted > 0) {
            (questionsCorrect.toFloat() / questionsAttempted.toFloat()) * 100
        } else {
            0f
        }
    }

    fun getProgressPercentage(): Float {
        return if (totalQuestions > 0) {
            (questionsAttempted.toFloat() / totalQuestions.toFloat()) * 100
        } else {
            0f
        }
    }

    fun getMasteryLevel(): MasteryLevel {
        val accuracy = getAccuracy()
        val progress = getProgressPercentage()

        return when {
            progress < 50 -> MasteryLevel.BEGINNER
            accuracy < 60 -> MasteryLevel.BEGINNER
            progress < 80 -> MasteryLevel.INTERMEDIATE
            accuracy < 80 -> MasteryLevel.INTERMEDIATE
            progress >= 80 && accuracy >= 80 -> MasteryLevel.ADVANCED
            else -> MasteryLevel.EXPERT
        }
    }

    enum class MasteryLevel {
        BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
    }
}
