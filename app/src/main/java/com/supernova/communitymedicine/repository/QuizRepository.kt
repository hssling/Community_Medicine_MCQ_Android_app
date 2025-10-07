package com.supernova.communitymedicine.repository

import com.supernova.communitymedicine.data.dao.QuizResultDao
import com.supernova.communitymedicine.data.dao.ChapterProgressDao
import com.supernova.communitymedicine.data.model.QuizResult
import com.supernova.communitymedicine.data.model.ChapterProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class QuizRepository(
    private val quizResultDao: QuizResultDao,
    private val chapterProgressDao: ChapterProgressDao
) {

    // QuizResult operations
    suspend fun insertQuizResult(quizResult: QuizResult): Long {
        return quizResultDao.insertQuizResult(quizResult)
    }

    suspend fun getQuizResultsByChapter(chapter: String): Flow<List<QuizResult>> {
        return quizResultDao.getQuizResultsByChapter(chapter)
    }

    suspend fun getBestScoreByChapter(chapter: String): QuizResult? {
        return quizResultDao.getBestScoreByChapter(chapter)
    }

    // ChapterProgress operations
    suspend fun insertChapterProgress(progress: ChapterProgress) {
        chapterProgressDao.insertChapterProgress(progress)
    }

    suspend fun getChapterProgress(chapterName: String): ChapterProgress? {
        return chapterProgressDao.getChapterProgress(chapterName)
    }

    suspend fun getAllChapterProgress(): List<ChapterProgress> {
        return chapterProgressDao.getAllChapterProgress().first()
    }

    fun getAllChapterProgressFlow(): Flow<List<ChapterProgress>> {
        return chapterProgressDao.getAllChapterProgress()
    }

    suspend fun updateChapterProgress(progress: ChapterProgress) {
        chapterProgressDao.updateChapterProgress(progress)
    }

    suspend fun updateProgress(
        chapterName: String,
        attempted: Int,
        correct: Int,
        wrong: Int,
        timeSpent: Long,
        timestamp: Long
    ) {
        chapterProgressDao.updateProgress(chapterName, attempted, correct, wrong, timeSpent, timestamp)
    }

    suspend fun updateBestScore(chapterName: String, score: Float) {
        chapterProgressDao.updateBestScore(chapterName, score)
    }

    suspend fun markChapterCompleted(chapterName: String) {
        chapterProgressDao.markChapterCompleted(chapterName)
    }

    // Combined operations for quiz completion
    suspend fun saveQuizResultAndUpdateProgress(
        quizResult: QuizResult,
        questionResults: Map<Long, Boolean> // questionId to isCorrect
    ) {
        // Insert quiz result
        insertQuizResult(quizResult)

        // Update chapter progress
        val chapterName = quizResult.chapter
        val attempted = quizResult.correctAnswers + quizResult.wrongAnswers
        val correct = quizResult.correctAnswers
        val wrong = quizResult.wrongAnswers
        val timeSpent = quizResult.timeTaken

        updateProgress(chapterName, attempted, correct, wrong, timeSpent, quizResult.dateTime)

        // Update best score if necessary
        val currentBest = getBestScoreByChapter(chapterName)
        if (currentBest == null || quizResult.percentage > currentBest.percentage) {
            updateBestScore(chapterName, quizResult.percentage)
        }

        // Mark chapter as completed if 100% score achieved
        if (quizResult.percentage >= 100f) {
            markChapterCompleted(chapterName)
        }
    }
}
