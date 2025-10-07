package com.supernova.communitymedicine

object Constants {

    // Community Medicine Chapters for NEET MBBS
    val COMMUNITY_MEDICINE_CHAPTERS = listOf(
        "Fundamentals of Community Medicine",
        "Epidemiology",
        "Biostatistics",
        "Environmental Health",
        "Nutrition and Malnutrition",
        "Maternal and Child Health",
        "Reproductive and Sexual Health",
        "Communicable Diseases",
        "Non-communicable Diseases",
        "Occupational Health",
        "Mental Health",
        "Health Systems and Health Care Delivery",
        "Demography",
        "Primary Health Care",
        "Health Planning and Management",
        "Research Methodology",
        "Community Dentistry"
    )

    // Difficulty levels
    const val DIFFICULTY_EASY = "Easy"
    const val DIFFICULTY_MEDIUM = "Medium"
    const val DIFFICULTY_HARD = "Hard"
    const val DIFFICULTY_MIXED = "Mixed"

    // Question Categories
    object Categories {
        const val FUNDAMENTALS = "Fundamentals of Community Medicine"
        const val EPIDEMIOLOGY = "Epidemiology"
        const val BIOSTATISTICS = "Biostatistics"
        const val ENVIRONMENTAL = "Environmental Health"
        const val NUTRITION = "Nutrition and Malnutrition"
        const val MATERNAL_CHILD = "Maternal and Child Health"
        const val REPRODUCTIVE = "Reproductive and Sexual Health"
        const val COMMUNICABLE = "Communicable Diseases"
        const val NON_COMMUNICABLE = "Non-communicable Diseases"
        const val OCCUPATIONAL = "Occupational Health"
        const val MENTAL_HEALTH = "Mental Health"
        const val HEALTH_SYSTEMS = "Health Systems and Health Care Delivery"
        const val DEMOGRAPHY = "Demography"
        const val PRIMARY_CARE = "Primary Health Care"
        const val PLANNING = "Health Planning and Management"
        const val RESEARCH = "Research Methodology"
    }

    // Time limits
    const val QUESTION_TIME_LIMIT_EASY = 60 // seconds
    const val QUESTION_TIME_LIMIT_MEDIUM = 45
    const val QUESTION_TIME_LIMIT_HARD = 30

    // Quiz settings
    const val DEFAULT_QUIZ_DURATION = 30 // minutes
    const val MIN_PASS_PERCENTAGE = 50

    // Achievement thresholds
    const val PERFECT_SCORE = 100
    const val EXCELLENT_SCORE = 90
    const val GOOD_SCORE = 80
    const val AVERAGE_SCORE = 70

    // Database
    const val DATABASE_NAME = "community_medicine_db"
    const val DATABASE_VERSION = 1

    // Shared Preferences
    const val PREFS_NAME = "community_medicine_prefs"
    const val PREF_FIRST_LAUNCH = "first_launch"
    const val PREF_USER_NAME = "user_name"
    const val PREF_STUDY_STREAK = "study_streak"
    const val PREF_TOTAL_QUESTIONS_ATTEMPTED = "total_questions_attempted"
    const val PREF_TOTAL_CORRECT_ANSWERS = "total_correct_answers"

    // UI Constants
    const val PROGRESS_ANIMATION_DURATION = 500L
    const val TRANSITION_ANIMATION_DURATION = 300L

    // Quiz related
    const val QUIZ_PASS_THRESHOLD = 60f // percentage
    const val MASTERY_THRESHOLD = 85f // percentage for mastery

    // Notification channels
    const val NOTIFICATION_CHANNEL_STUDY_REMINDER = "study_reminder"
    const val NOTIFICATION_CHANNEL_ACHIEVEMENT = "achievement"
    const val NOTIFICATION_CHANNEL_QUIZ_COMPLETE = "quiz_complete"

    // App features
    const val MAX_BOOKMARKS = 100
    const val MAX_QUIZZES_HISTORY = 50
    const val SYNC_INTERVAL_HOURS = 24
}
