package com.supernova.communitymedicine.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.supernova.communitymedicine.data.dao.QuestionDao
import com.supernova.communitymedicine.data.dao.QuizResultDao
import com.supernova.communitymedicine.data.dao.ChapterProgressDao
import com.supernova.communitymedicine.data.dao.BookmarksDao
import com.supernova.communitymedicine.data.model.Question
import com.supernova.communitymedicine.data.model.QuizResult
import com.supernova.communitymedicine.data.model.ChapterProgress
import com.supernova.communitymedicine.data.model.Bookmark
import com.supernova.communitymedicine.data.database.converters.Converters

@Database(
    entities = [Question::class, QuizResult::class, ChapterProgress::class, Bookmark::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun quizResultDao(): QuizResultDao
    abstract fun chapterProgressDao(): ChapterProgressDao
    abstract fun bookmarksDao(): BookmarksDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "community_medicine_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
