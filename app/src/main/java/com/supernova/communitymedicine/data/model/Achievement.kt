package com.supernova.communitymedicine.data.model

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconResId: Int,
    val badgeColor: Int,
    val isUnlocked: Boolean,
    val unlockedDate: Long?,
    val progress: Float, // 0-100
    val maxProgress: Float,
    val category: AchievementCategory,
    val rarity: AchievementRarity
) {
    fun getProgressPercentage(): Float {
        return if (maxProgress > 0) (progress / maxProgress) * 100 else 0f
    }

    enum class AchievementCategory {
        QUIZ, CHAPTER, STREAK, ACCURACY, STUDY, SPECIAL
    }

    enum class AchievementRarity {
        COMMON, RARE, EPIC, LEGENDARY
    }
}
