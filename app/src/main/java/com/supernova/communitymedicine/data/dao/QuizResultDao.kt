package com.supernova.communitymedicine.data.dao

import androidx.room.*
import com.supernova.communitymedicine.data.model.QuizResult
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(quizResult: QuizResult): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResults(quizResults: List<QuizResult>)

    @Update
    suspend fun updateQuizResult(quizResult: QuizResult)

    @Delete
    suspend fun deleteQuizResult(quizResult: QuizResult)

    @Query("SELECT * FROM quiz_results WHERE id = :resultId")
    suspend fun getQuizResultById(resultId: Long): QuizResult?

    @Query("SELECT * FROM quiz_results WHERE chapter = :chapter ORDER BY dateTime DESC")
    fun getQuizResultsByChapter(chapter: String): Flow<List<QuizResult>>

    @Query("SELECT * FROM quiz_results ORDER BY dateTime DESC")
    fun getAllQuizResults(): Flow<List<QuizResult>>

    @Query("SELECT * FROM quiz_results WHERE chapter = :chapter ORDER BY percentage DESC LIMIT 1")
    suspend fun getBestScoreByChapter(chapter: String): QuizResult?

    @Query("SELECT AVG(percentage) FROM quiz_results WHERE chapter = :chapter")
    suspend fun getAverageScoreByChapter(chapter: String): Float?

    @Query("SELECT COUNT(*) FROM quiz_results WHERE chapter = :chapter")
    suspend fun getQuizCountByChapter(chapter: String): Int

    @Query("SELECT SUM(timeTaken) FROM quiz_results WHERE chapter = :chapter")
    suspend fun getTotalTimeByChapter(chapter: String): Long?

    @Query("SELECT * FROM quiz_results WHERE dateTime >= :startTime ORDER BY percentage DESC LIMIT :limit")
    suspend fun getRecentTopScores(startTime: Long, limit: Int): List<QuizResult>

    @Query("SELECT * FROM quiz_results ORDER BY percentage DESC LIMIT :limit")
    suspend fun getTopScores(limit: Int): List<QuizResult>

    @Query("SELECT COUNT(*) FROM quiz_results WHERE percentage >= 80")
    suspend fun getHighScoreCount(): Int

    @Query("SELECT AVG(percentage) FROM quiz_results")
    suspend fun getOverallAverageScore(): Float?

    @Query("SELECT SUM(timeTaken) FROM quiz_results")
    suspend fun getTotalStudyTime(): Long?

    @Query("SELECT COUNT(*) FROM quiz_results")
    suspend fun getTotalQuizzesCompleted(): Int
}
