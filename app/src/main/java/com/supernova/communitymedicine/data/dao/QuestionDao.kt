package com.supernova.communitymedicine.data.dao

import androidx.room.*
import com.supernova.communitymedicine.data.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    // Basic CRUD operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Update
    suspend fun updateQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("SELECT * FROM questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId: Long): Question?

    @Query("SELECT * FROM questions WHERE chapter = :chapter ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomQuestionsByChapter(chapter: String, count: Int): List<Question>

    @Query("SELECT * FROM questions WHERE chapter = :chapter AND difficulty = :difficulty ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomQuestionsByChapterAndDifficulty(chapter: String, difficulty: String, count: Int): List<Question>

    @Query("SELECT * FROM questions WHERE chapter = :chapter ORDER BY RANDOM()")
    fun getAllQuestionsByChapter(chapter: String): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE isBookmarked = 1 ORDER BY lastAnswered DESC")
    fun getBookmarkedQuestions(): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE timesAnswered > 0 ORDER BY CASE WHEN timesAnswered > 0 THEN (timesCorrect * 1.0) / timesAnswered ELSE 1.0 END ASC")
    fun getWeakQuestions(): Flow<List<Question>>

    @Query("SELECT DISTINCT chapter FROM questions ORDER BY chapter")
    fun getAllChapters(): Flow<List<String>>

    @Query("SELECT COUNT(*) FROM questions WHERE chapter = :chapter")
    suspend fun getQuestionCountByChapter(chapter: String): Int

    @Query("SELECT COUNT(*) FROM questions WHERE chapter = :chapter AND difficulty = :difficulty")
    suspend fun getQuestionCountByChapterAndDifficulty(chapter: String, difficulty: String): Int

    // Progress tracking queries
    @Query("SELECT * FROM questions WHERE chapter = :chapter AND timesAnswered > 0")
    suspend fun getAttemptedQuestionsByChapter(chapter: String): List<Question>

    @Query("SELECT * FROM questions WHERE chapter = :chapter AND timesCorrect > 0")
    suspend fun getCorrectQuestionsByChapter(chapter: String): List<Question>

    @Query("SELECT * FROM questions WHERE chapter = :chapter AND timesAnswered > 0 AND timesCorrect = 0")
    suspend fun getIncorrectQuestionsByChapter(chapter: String): List<Question>

    // Update progress methods
    @Query("UPDATE questions SET timesAnswered = timesAnswered + 1, timesCorrect = timesCorrect + 1, lastAnswered = :timestamp WHERE id = :questionId")
    suspend fun markQuestionCorrect(questionId: Long, timestamp: Long)

    @Query("UPDATE questions SET timesAnswered = timesAnswered + 1, lastAnswered = :timestamp WHERE id = :questionId")
    suspend fun markQuestionIncorrect(questionId: Long, timestamp: Long)

    @Query("UPDATE questions SET isBookmarked = :isBookmarked WHERE id = :questionId")
    suspend fun updateBookmarkStatus(questionId: Long, isBookmarked: Boolean)

    // Statistics queries
    @Query("SELECT AVG(CAST(timesCorrect AS FLOAT) / timesAnswered) * 100 FROM questions WHERE timesAnswered > 0 AND chapter = :chapter")
    suspend fun getAverageAccuracyByChapter(chapter: String): Float?

    @Query("SELECT COUNT(*) FROM questions WHERE timesAnswered > 0 AND chapter = :chapter")
    suspend fun getAttemptedCountByChapter(chapter: String): Int

    @Query("SELECT COUNT(*) FROM questions WHERE timesCorrect > 0 AND chapter = :chapter")
    suspend fun getCorrectCountByChapter(chapter: String): Int
}
