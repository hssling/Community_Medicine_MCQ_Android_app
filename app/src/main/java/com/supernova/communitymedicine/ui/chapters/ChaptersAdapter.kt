package com.supernova.communitymedicine.ui.chapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.supernova.communitymedicine.R
import com.supernova.communitymedicine.data.model.ChapterProgress
import com.supernova.communitymedicine.databinding.ItemChapterBinding

class ChaptersAdapter(
    private val onChapterClick: (ChapterProgress) -> Unit
) : ListAdapter<ChapterProgress, ChaptersAdapter.ChapterViewHolder>(ChapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val binding = ItemChapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter = getItem(position)
        holder.bind(chapter)
        holder.itemView.setOnClickListener { onChapterClick(chapter) }
    }

    class ChapterViewHolder(
        private val binding: ItemChapterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chapter: ChapterProgress) {
            binding.tvChapterName.text = chapter.chapterName

            // Set progress
            binding.progressChapter.progress = chapter.getProgressPercentage().toInt()
            binding.tvProgress.text = binding.root.context.getString(
                R.string.chapter_progress_percentage,
                chapter.getProgressPercentage().toInt()
            )

            // Set question count
            binding.tvQuestionCount.text = binding.root.context.getString(
                R.string.questions_count,
                chapter.totalQuestions
            )

            // Set best score if available
            if (chapter.bestScore > 0) {
                binding.tvBestScore.text = binding.root.context.getString(
                    R.string.best_score,
                    chapter.bestScore
                )
                binding.tvBestScore.visibility = View.VISIBLE
            } else {
                binding.tvBestScore.visibility = View.GONE
            }

            // Set accuracy if questions attempted
            if (chapter.questionsAttempted > 0) {
                binding.tvAccuracy.text = binding.root.context.getString(
                    R.string.accuracy_percentage,
                    chapter.getAccuracy()
                )
                binding.tvAccuracy.visibility = View.VISIBLE
            } else {
                binding.tvAccuracy.visibility = View.GONE
            }

            // Set mastery level
            val masteryLevel = chapter.getMasteryLevel()
            binding.tvMasteryLevel.text = masteryLevel.name
            binding.tvMasteryLevel.setTextColor(getMasteryLevelColor(masteryLevel))

            // Set completion status
            when {
                chapter.isCompleted -> {
                    binding.tvStatus.text = binding.root.context.getString(R.string.completed)
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.correct_answer)
                    )
                }
                chapter.questionsAttempted > 0 -> {
                    binding.tvStatus.text = binding.root.context.getString(R.string.in_progress)
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.progress_average)
                    )
                }
                else -> {
                    binding.tvStatus.text = binding.root.context.getString(R.string.not_started)
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(binding.root.context, R.color.secondary_text)
                    )
                }
            }

            // Set progress bar color based on mastery level
            val progressColor = when (masteryLevel) {
                ChapterProgress.MasteryLevel.EXPERT -> R.color.progress_excellent
                ChapterProgress.MasteryLevel.ADVANCED -> R.color.progress_good
                ChapterProgress.MasteryLevel.INTERMEDIATE -> R.color.progress_average
                ChapterProgress.MasteryLevel.BEGINNER -> R.color.progress_poor
            }
            binding.progressChapter.progressTintList = ContextCompat.getColorStateList(
                binding.root.context,
                progressColor
            )

            // Set streak indicator
            if (chapter.currentStreak > 0) {
                binding.tvStreak.text = binding.root.context.getString(
                    R.string.study_streak_days,
                    chapter.currentStreak
                )
                binding.tvStreak.visibility = View.VISIBLE
            } else {
                binding.tvStreak.visibility = View.GONE
            }
        }

        private fun getMasteryLevelColor(level: ChapterProgress.MasteryLevel): Int {
            return when (level) {
                ChapterProgress.MasteryLevel.EXPERT -> R.color.progress_excellent
                ChapterProgress.MasteryLevel.ADVANCED -> R.color.progress_good
                ChapterProgress.MasteryLevel.INTERMEDIATE -> R.color.progress_average
                ChapterProgress.MasteryLevel.BEGINNER -> R.color.progress_poor
            }
        }
    }

    class ChapterDiffCallback : DiffUtil.ItemCallback<ChapterProgress>() {
        override fun areItemsTheSame(oldItem: ChapterProgress, newItem: ChapterProgress): Boolean {
            return oldItem.chapterName == newItem.chapterName
        }

        override fun areContentsTheSame(oldItem: ChapterProgress, newItem: ChapterProgress): Boolean {
            return oldItem == newItem
        }
    }
}
