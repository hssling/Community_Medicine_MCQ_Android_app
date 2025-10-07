package com.supernova.communitymedicine.data.model

data class OverallProgress(
    val totalQuestions: Int,
    val attemptedQuestions: Int,
    val correctAnswers: Int,
    val completionPercentage: Float,
    val currentStreak: Int
) {
    fun getAccuracy(): Float {
        return if (attemptedQuestions > 0) {
            (correctAnswers.toFloat() / attemptedQuestions.toFloat()) * 100
        } else {
            0f
        }
    }

    fun getProgressPercentage(): Float {
        return if (totalQuestions > 0) {
            (attemptedQuestions.toFloat() / totalQuestions.toFloat()) * 100
        } else {
            0f
        }
    }
}
