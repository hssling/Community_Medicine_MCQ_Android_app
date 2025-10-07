package com.supernova.communitymedicine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statistics")
data class Statistics(
    @PrimaryKey
    val id: String = "user_stats",
    val overallProgress: Float,
    val totalQuestions: Int,
    val overallAccuracy: Float,
    val currentStreak: Int,
    val bestStreak: Int,
    val quizzesCompleted: Int,
    val averageScore: Float,
    val totalStudyTime: Long, // in seconds
    val chaptersCompleted: Int,
    val totalChapters: Int,

    // Achievement data
    val totalAchievements: Int,
    val unlockedAchievements: Int,

    // Recent activity
    val lastQuizDate: Long?,
    val weeklyProgress: Float,
    val monthlyProgress: Float
) {
    fun getStudyTimeFormatted(): String {
        val hours = totalStudyTime / 3600
        val minutes = (totalStudyTime % 3600) / 60
        return if (hours > 0) {
            "${hours}h ${minutes}m"
        } else {
            "${minutes}m"
        }
    }

    fun getCompletionRate(): Float {
        return if (totalChapters > 0) {
            (chaptersCompleted.toFloat() / totalChapters.toFloat()) * 100
        } else {
            0f
        }
    }

    fun getAchievementProgress(): Float {
        return if (totalAchievements > 0) {
            (unlockedAchievements.toFloat() / totalAchievements.toFloat()) * 100
        } else {
            0f
        }
    }
}
