package com.supernova.communitymedicine.data.dao

import androidx.room.*
import com.supernova.communitymedicine.data.model.ChapterProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapterProgress(progress: ChapterProgress)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapterProgresses(progresses: List<ChapterProgress>)

    @Update
    suspend fun updateChapterProgress(progress: ChapterProgress)

    @Delete
    suspend fun deleteChapterProgress(progress: ChapterProgress)

    @Query("SELECT * FROM chapter_progress WHERE chapterName = :chapterName")
    suspend fun getChapterProgress(chapterName: String): ChapterProgress?

    @Query("SELECT * FROM chapter_progress ORDER BY completionPercentage DESC, bestScore DESC")
    fun getAllChapterProgress(): Flow<List<ChapterProgress>>

    @Query("SELECT * FROM chapter_progress WHERE isCompleted = 1 ORDER BY completionPercentage DESC")
    fun getCompletedChapters(): Flow<List<ChapterProgress>>

    @Query("SELECT * FROM chapter_progress WHERE isCompleted = 0 ORDER BY completionPercentage DESC")
    fun getIncompleteChapters(): Flow<List<ChapterProgress>>

    @Query("SELECT COUNT(*) FROM chapter_progress WHERE isCompleted = 1")
    suspend fun getCompletedChapterCount(): Int

    @Query("SELECT COUNT(*) FROM chapter_progress")
    suspend fun getTotalChapterCount(): Int

    @Query("SELECT AVG(completionPercentage) FROM chapter_progress")
    suspend fun getOverallCompletionPercentage(): Float?

    @Query("SELECT AVG(bestScore) FROM chapter_progress WHERE bestScore > 0")
    suspend fun getAverageBestScore(): Float?

    @Query("SELECT SUM(totalTimeSpent) FROM chapter_progress")
    suspend fun getTotalStudyTime(): Long?

    @Query("SELECT * FROM chapter_progress ORDER BY lastAccessed DESC LIMIT :limit")
    suspend fun getRecentlyAccessedChapters(limit: Int): List<ChapterProgress>

    @Query("SELECT * FROM chapter_progress WHERE currentStreak > 0 ORDER BY currentStreak DESC")
    fun getChaptersWithActiveStreaks(): Flow<List<ChapterProgress>>

    @Query("SELECT * FROM chapter_progress ORDER BY bestStreak DESC LIMIT :limit")
    suspend fun getTopStreakChapters(limit: Int): List<ChapterProgress>

    // Update methods for progress tracking
    @Query("UPDATE chapter_progress SET questionsAttempted = questionsAttempted + :attempted, questionsCorrect = questionsCorrect + :correct, questionsWrong = questionsWrong + :wrong, totalTimeSpent = totalTimeSpent + :timeSpent, quizzesCompleted = quizzesCompleted + 1, lastAccessed = :timestamp WHERE chapterName = :chapterName")
    suspend fun updateProgress(chapterName: String, attempted: Int, correct: Int, wrong: Int, timeSpent: Long, timestamp: Long)

    @Query("UPDATE chapter_progress SET bestScore = :score WHERE chapterName = :chapterName AND (bestScore < :score OR bestScore = 0)")
    suspend fun updateBestScore(chapterName: String, score: Float)

    @Query("UPDATE chapter_progress SET averageScore = :averageScore WHERE chapterName = :chapterName")
    suspend fun updateAverageScore(chapterName: String, averageScore: Float)

    @Query("UPDATE chapter_progress SET currentStreak = currentStreak + 1, bestStreak = MAX(bestStreak, currentStreak + 1) WHERE chapterName = :chapterName")
    suspend fun incrementStreak(chapterName: String)

    @Query("UPDATE chapter_progress SET currentStreak = 0 WHERE chapterName = :chapterName")
    suspend fun resetStreak(chapterName: String)

    @Query("UPDATE chapter_progress SET isCompleted = 1, completionPercentage = 100 WHERE chapterName = :chapterName")
    suspend fun markChapterCompleted(chapterName: String)

    // Difficulty-specific updates
    @Query("UPDATE chapter_progress SET easyQuestionsCorrect = easyQuestionsCorrect + :count WHERE chapterName = :chapterName")
    suspend fun incrementEasyCorrect(chapterName: String, count: Int = 1)

    @Query("UPDATE chapter_progress SET mediumQuestionsCorrect = mediumQuestionsCorrect + :count WHERE chapterName = :chapterName")
    suspend fun incrementMediumCorrect(chapterName: String, count: Int = 1)

    @Query("UPDATE chapter_progress SET hardQuestionsCorrect = hardQuestionsCorrect + :count WHERE chapterName = :chapterName")
    suspend fun incrementHardCorrect(chapterName: String, count: Int = 1)
}
