package com.supernova.communitymedicine.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val dateTime: Long,
    val chapter: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val skippedQuestions: Int,
    val timeTaken: Long, // in seconds
    val score: Float,
    val percentage: Float,
    val difficulty: String,

    // Detailed results
    val questionsAnswered: List<Long>, // List of question IDs
    val correctQuestionIds: List<Long>,
    val wrongQuestionIds: List<Long>,
    val skippedQuestionIds: List<Long>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString() ?: "",
        parcel.createLongArray()?.toList() ?: emptyList(),
        parcel.createLongArray()?.toList() ?: emptyList(),
        parcel.createLongArray()?.toList() ?: emptyList(),
        parcel.createLongArray()?.toList() ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(dateTime)
        parcel.writeString(chapter)
        parcel.writeInt(totalQuestions)
        parcel.writeInt(correctAnswers)
        parcel.writeInt(wrongAnswers)
        parcel.writeInt(skippedQuestions)
        parcel.writeLong(timeTaken)
        parcel.writeFloat(score)
        parcel.writeFloat(percentage)
        parcel.writeString(difficulty)
        parcel.writeLongArray(questionsAnswered.toLongArray())
        parcel.writeLongArray(correctQuestionIds.toLongArray())
        parcel.writeLongArray(wrongQuestionIds.toLongArray())
        parcel.writeLongArray(skippedQuestionIds.toLongArray())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuizResult> {
        override fun createFromParcel(parcel: Parcel): QuizResult {
            return QuizResult(parcel)
        }

        override fun newArray(size: Int): Array<QuizResult?> {
            return arrayOfNulls(size)
        }
    }

    fun getAccuracy(): Float {
        return if (totalQuestions > 0) {
            (correctAnswers.toFloat() / totalQuestions.toFloat()) * 100
        } else {
            0f
        }
    }

    fun getCompletionRate(): Float {
        val answered = correctAnswers + wrongAnswers
        return if (totalQuestions > 0) {
            (answered.toFloat() / totalQuestions.toFloat()) * 100
        } else {
            0f
        }
    }
}
