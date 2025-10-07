package com.supernova.communitymedicine.utils

import android.content.Context
import android.content.SharedPreferences
import com.supernova.communitymedicine.Constants
import com.supernova.communitymedicine.data.database.AppDatabase
import com.supernova.communitymedicine.data.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseSeeder(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
    private val database = AppDatabase.getDatabase(context)

    suspend fun seedDatabaseIfNeeded() = withContext(Dispatchers.IO) {
        val isSeeded = prefs.getBoolean(PREF_DATABASE_SEEDED, false)

        if (!isSeeded) {
            try {
                // Clear existing data (if any)
                database.questionDao().clearAllQuestions()

                // Seed with new data
                val questions = MCQDataProvider.getAllCommunityMedicineQuestions()
                database.questionDao().insertQuestions(questions)

                // Mark as seeded
                prefs.edit().putBoolean(PREF_DATABASE_SEEDED, true).apply()

                // Also seed chapters if not already done
                seedChapters()

            } catch (e: Exception) {
                // Reset flag if seeding failed
                prefs.edit().putBoolean(PREF_DATABASE_SEEDED, false).apply()
                throw e
            }
        }
    }

    private suspend fun seedChapters() = withContext(Dispatchers.IO) {
        val isChaptersSeeded = prefs.getBoolean(PREF_CHAPTERS_SEEDED, false)

        if (!isChaptersSeeded) {
            try {
                // Initialize chapters with zero questions (will be updated later)
                val chapters = Constants.COMMUNITY_MEDICINE_CHAPTERS.map { chapterName ->
                    com.supernova.communitymedicine.data.model.ChapterProgress(
                        chapterName = chapterName,
                        totalQuestions = 0, // Will be calculated later
                        questionsAttempted = 0,
                        questionsCorrect = 0,
                        bestScore = 0f,
                        quizzesCompleted = 0,
                        currentStreak = 0
                    )
                }

                database.chapterProgressDao().insertChapters(chapters)

                // Update chapter counts based on actual questions
                updateChapterCounts()

                prefs.edit().putBoolean(PREF_CHAPTERS_SEEDED, true).apply()

            } catch (e: Exception) {
                prefs.edit().putBoolean(PREF_CHAPTERS_SEEDED, false).apply()
                throw e
            }
        }
    }

    private suspend fun updateChapterCounts() = withContext(Dispatchers.IO) {
        val questionCounts = database.questionDao().getChapterQuestionCounts()

        questionCounts.forEach { (chapter, count) ->
            database.chapterProgressDao().updateChapterQuestionCount(chapter, count)
        }
    }

    companion object {
        private const val PREF_DATABASE_SEEDED = "database_seeded_v2" // Versioned to allow reseeding
        private const val PREF_CHAPTERS_SEEDED = "chapters_seeded_v2"
    }
}
