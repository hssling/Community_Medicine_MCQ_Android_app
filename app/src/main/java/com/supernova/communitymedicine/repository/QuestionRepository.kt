package com.supernova.communitymedicine.repository

import com.supernova.communitymedicine.data.dao.QuestionDao
import com.supernova.communitymedicine.data.model.Question
import kotlinx.coroutines.flow.Flow

class QuestionRepository(private val questionDao: QuestionDao) {

    suspend fun insertQuestion(question: Question): Long {
        return questionDao.insertQuestion(question)
    }

    suspend fun insertQuestions(questions: List<Question>) {
        questionDao.insertQuestions(questions)
    }

    suspend fun updateQuestion(question: Question) {
        questionDao.updateQuestion(question)
    }

    suspend fun deleteQuestion(question: Question) {
        questionDao.deleteQuestion(question)
    }

    suspend fun getQuestionById(questionId: Long): Question? {
        return questionDao.getQuestionById(questionId)
    }

    suspend fun getRandomQuestionsByChapter(chapter: String, count: Int): List<Question> {
        return questionDao.getRandomQuestionsByChapter(chapter, count)
    }

    suspend fun getRandomQuestionsByChapterAndDifficulty(
        chapter: String,
        difficulty: String,
        count: Int
    ): List<Question> {
        return questionDao.getRandomQuestionsByChapterAndDifficulty(chapter, difficulty, count)
    }

    fun getAllQuestionsByChapter(chapter: String): Flow<List<Question>> {
        return questionDao.getAllQuestionsByChapter(chapter)
    }

    fun getBookmarkedQuestions(): Flow<List<Question>> {
        return questionDao.getBookmarkedQuestions()
    }

    fun getWeakQuestions(): Flow<List<Question>> {
        return questionDao.getWeakQuestions()
    }

    fun getAllChapters(): Flow<List<String>> {
        return questionDao.getAllChapters()
    }

    suspend fun getQuestionCountByChapter(chapter: String): Int {
        return questionDao.getQuestionCountByChapter(chapter)
    }

    suspend fun markQuestionCorrect(questionId: Long, timestamp: Long) {
        questionDao.markQuestionCorrect(questionId, timestamp)
    }

    suspend fun markQuestionIncorrect(questionId: Long, timestamp: Long) {
        questionDao.markQuestionIncorrect(questionId, timestamp)
    }

    suspend fun updateBookmarkStatus(questionId: Long, isBookmarked: Boolean) {
        questionDao.updateBookmarkStatus(questionId, isBookmarked)
    }
}
