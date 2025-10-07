package com.supernova.communitymedicine.data.model

data class ChapterStatistics(
    val chapterName: String,
    val totalQuestions: Int,
    val questionsAttempted: Int,
    val questionsCorrect: Int,
    val bestScore: Float,
    val averageScore: Float,
    val quizzesCompleted: Int,
    val totalTimeSpent: Long,
    val isCompleted: Boolean,
    val lastAccessed: Long,
    val currentStreak: Int,
    val bestStreak: Int,
    val masteryLevel: ChapterProgress.MasteryLevel,

    // Difficulty breakdown
    val easyCorrect: Int,
    val mediumCorrect: Int,
    val hardCorrect: Int,

    // Recent performance
    val recentScores: List<Float>,
    val improvementTrend: Float // positive = improving, negative = declining
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

    fun getStudyTimeFormatted(): String {
        val hours = totalTimeSpent / 3600
        val minutes = (totalTimeSpent % 3600) / 60
        return if (hours > 0) {
            "${hours}h ${minutes}m"
        } else {
            "${minutes}m"
        }
    }

    fun getDifficultyDistribution(): Map<String, Int> {
        return mapOf(
            "Easy" to easyCorrect,
            "Medium" to mediumCorrect,
            "Hard" to hardCorrect
        )
    }

    fun getAverageScoreRecent(): Float {
        return if (recentScores.isNotEmpty()) {
            recentScores.average().toFloat()
        } else {
            averageScore
        }
    }
}
