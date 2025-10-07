package com.supernova.communitymedicine.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey
    val id: String,
    val questionId: Long,
    val questionText: String,
    val questionOptions: List<String>,
    val correctAnswer: String,
    val chapter: String,
    val difficulty: String,
    val note: String?, // Custom notes added by user
    val tag: String?, // Custom tag for organization
    val dateBookmarked: Long,
    val lastReviewed: Long?,
    val reviewCount: Int
) {
    fun getTimeAgo(): String {
        val now = System.currentTimeMillis()
        val diffMillis = now - dateBookmarked

        val seconds = diffMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            seconds < 60 -> "Just now"
            minutes < 60 -> "${minutes}m ago"
            hours < 24 -> "${hours}h ago"
            else -> "${days}d ago"
        }
    }

    fun getReviewFrequencyText(): String {
        return when (reviewCount) {
            0 -> "Never reviewed"
            1 -> "Reviewed once"
            else -> "Reviewed $reviewCount times"
        }
    }
}
