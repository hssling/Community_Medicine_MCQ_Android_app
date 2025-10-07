package com.supernova.communitymedicine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String,
    val explanation: String,
    val chapter: String,
    val difficulty: String,
    val marks: Int = 1,
    val timeLimit: Int = 60, // seconds

    // Progress tracking
    val timesAnswered: Int = 0,
    val timesCorrect: Int = 0,
    val lastAnswered: Long? = null,
    val isBookmarked: Boolean = false
) {
    fun getCorrectAnswerIndex(): Int {
        return when (correctAnswer.uppercase()) {
            "A" -> 0
            "B" -> 1
            "C" -> 2
            "D" -> 3
            else -> -1
        }
    }

    fun getOptions(): List<String> {
        return listOf(optionA, optionB, optionC, optionD)
    }

    fun getAccuracy(): Float {
        return if (timesAnswered > 0) {
            (timesCorrect.toFloat() / timesAnswered.toFloat()) * 100
        } else {
            0f
        }
    }
}
