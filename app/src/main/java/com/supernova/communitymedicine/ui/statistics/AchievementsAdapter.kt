package com.supernova.communitymedicine.ui.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.supernova.communitymedicine.R
import com.supernova.communitymedicine.data.model.Achievement
import com.supernova.communitymedicine.databinding.ItemAchievementBinding

class AchievementsAdapter :
    ListAdapter<Achievement, AchievementsAdapter.AchievementViewHolder>(AchievementDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val binding = ItemAchievementBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AchievementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = getItem(position)
        holder.bind(achievement)
    }

    class AchievementViewHolder(
        private val binding: ItemAchievementBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(achievement: Achievement) {
            binding.tvAchievementTitle.text = achievement.title
            binding.tvAchievementDescription.text = achievement.description

            // Set achievement icon (placeholder for now)
            binding.ivAchievementIcon.setImageResource(R.drawable.ic_stats)

            // Set badge background color based on rarity
            val badgeColor = when (achievement.rarity) {
                Achievement.AchievementRarity.COMMON -> R.color.progress_good
                Achievement.AchievementRarity.RARE -> R.color.progress_average
                Achievement.AchievementRarity.EPIC -> R.color.progress_excellent
                Achievement.AchievementRarity.LEGENDARY -> R.color.gold
            }
            binding.cardAchievement.setCardBackgroundColor(
                ContextCompat.getColor(binding.root.context, badgeColor)
            )

            // Show progress if not completed
            if (achievement.isUnlocked) {
                binding.tvAchievementProgress.text = "Unlocked!"
                binding.tvAchievementProgress.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.correct_answer)
                )
                binding.progressAchievement.visibility = View.GONE
            } else {
                binding.tvAchievementProgress.text = "${achievement.getProgressPercentage().toInt()}% Complete"
                binding.tvAchievementProgress.setTextColor(
                    ContextCompat.getColor(binding.root.context, R.color.secondary_text)
                )
                binding.progressAchievement.visibility = View.VISIBLE
                binding.progressAchievement.progress = achievement.getProgressPercentage().toInt()
            }

            // Show unlocked date if available
            if (achievement.isUnlocked && achievement.unlockedDate != null) {
                binding.tvUnlockedDate.text = "Unlocked: ${formatDate(achievement.unlockedDate)}"
                binding.tvUnlockedDate.visibility = View.VISIBLE
            } else {
                binding.tvUnlockedDate.visibility = View.GONE
            }

            // Set category icon
            val categoryIcon = when (achievement.category) {
                Achievement.AchievementCategory.QUIZ -> R.drawable.ic_check
                Achievement.AchievementCategory.CHAPTER -> R.drawable.ic_bookmark
                Achievement.AchievementCategory.STREAK -> R.drawable.ic_stats
                Achievement.AchievementCategory.ACCURACY -> R.drawable.ic_stats
                Achievement.AchievementCategory.STUDY -> R.drawable.ic_stats
                Achievement.AchievementCategory.SPECIAL -> R.drawable.ic_stats
            }
            binding.ivCategoryIcon.setImageResource(categoryIcon)
        }

        private fun formatDate(timestamp: Long): String {
            val date = java.util.Date(timestamp)
            val format = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
            return format.format(date)
        }
    }

    class AchievementDiffCallback : DiffUtil.ItemCallback<Achievement>() {
        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem == newItem
        }
    }
}
