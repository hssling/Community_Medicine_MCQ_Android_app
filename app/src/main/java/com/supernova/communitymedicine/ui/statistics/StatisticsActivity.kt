package com.supernova.communitymedicine.ui.statistics

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.supernova.communitymedicine.R
import com.supernova.communitymedicine.databinding.ActivityStatisticsBinding
import com.supernova.communitymedicine.viewmodel.StatisticsViewModel
import kotlinx.coroutines.launch

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private val viewModel: StatisticsViewModel by viewModels()
    private lateinit var achievementsAdapter: AchievementsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
        loadStatistics()
    }

    private fun setupUI() {
        // Set up toolbar
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Set up RecyclerView for achievements
        achievementsAdapter = AchievementsAdapter()
        binding.recyclerAchievements.apply {
            layoutManager = LinearLayoutManager(this@StatisticsActivity)
            adapter = achievementsAdapter
        }

        // Set up refresh layout
        binding.swipeRefresh.setOnRefreshListener {
            loadStatistics()
        }

        // Set up tab selection
        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showOverviewTab()
                    1 -> showChaptersTab()
                    2 -> showAchievementsTab()
                    3 -> showHistoryTab()
                }
            }

            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
            binding.progressBar.visibility = if (isLoading && !binding.swipeRefresh.isRefreshing) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(it)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        viewModel.statistics.observe(this) { stats ->
            displayOverviewStats(stats)
        }

        viewModel.chapterStats.observe(this) { chapterStats ->
            displayChapterStats(chapterStats)
        }

        viewModel.achievements.observe(this) { achievements ->
            achievementsAdapter.submitList(achievements)
            binding.tvEmptyAchievements.visibility = if (achievements.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.quizHistory.observe(this) { history ->
            displayQuizHistory(history)
        }
    }

    private fun loadStatistics() {
        lifecycleScope.launch {
            try {
                viewModel.loadStatistics()
            } catch (e: Exception) {
                androidx.appcompat.app.AlertDialog.Builder(this@StatisticsActivity)
                    .setTitle("Error")
                    .setMessage("Error loading statistics: ${e.message}")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    private fun displayOverviewStats(stats: com.supernova.communitymedicine.data.model.Statistics) {
        // Overall progress
        binding.tvOverallProgress.text = "${stats.overallProgress.toInt()}%"
        binding.progressOverall.progress = stats.overallProgress.toInt()

        // Total questions and accuracy
        binding.tvTotalQuestions.text = stats.totalQuestions.toString()
        binding.tvOverallAccuracy.text = "${stats.overallAccuracy.toInt()}%"

        // Study streak
        binding.tvCurrentStreak.text = "${stats.currentStreak} days"
        binding.tvBestStreak.text = "${stats.bestStreak} days"

        // Quiz statistics
        binding.tvQuizzesCompleted.text = stats.quizzesCompleted.toString()
        binding.tvAverageScore.text = "${stats.averageScore.toInt()}%"
        binding.tvTotalStudyTime.text = formatTime(stats.totalStudyTime)

        // Update progress colors based on performance
        updateProgressColors(stats.overallAccuracy)
    }

    private fun displayChapterStats(chapterStats: List<com.supernova.communitymedicine.data.model.ChapterStatistics>) {
        // Update chapter-specific statistics
        // This would typically update a RecyclerView or detailed view
        binding.tvChaptersCompleted.text = chapterStats.count { it.isCompleted }.toString()
        binding.tvTotalChapters.text = chapterStats.size.toString()
    }

    private fun displayQuizHistory(history: List<com.supernova.communitymedicine.data.model.QuizResult>) {
        // Display recent quiz history
        binding.tvRecentActivity.text = "Last ${history.size} quizzes completed"
    }

    private fun updateProgressColors(accuracy: Float) {
        val color = when {
            accuracy >= 90 -> R.color.progress_excellent
            accuracy >= 75 -> R.color.progress_good
            accuracy >= 60 -> R.color.progress_average
            else -> R.color.progress_poor
        }

        binding.progressOverall.progressTintList = androidx.core.content.ContextCompat.getColorStateList(this, color)
        binding.tvOverallAccuracy.setTextColor(getColor(color))
    }

    private fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        return if (hours > 0) {
            "${hours}h ${minutes}m"
        } else {
            "${minutes}m"
        }
    }

    private fun showOverviewTab() {
        binding.contentOverview.visibility = View.VISIBLE
        binding.contentChapters.visibility = View.GONE
        binding.contentAchievements.visibility = View.GONE
        binding.contentHistory.visibility = View.GONE
    }

    private fun showChaptersTab() {
        binding.contentOverview.visibility = View.GONE
        binding.contentChapters.visibility = View.VISIBLE
        binding.contentAchievements.visibility = View.GONE
        binding.contentHistory.visibility = View.GONE
    }

    private fun showAchievementsTab() {
        binding.contentOverview.visibility = View.GONE
        binding.contentChapters.visibility = View.GONE
        binding.contentAchievements.visibility = View.VISIBLE
        binding.contentHistory.visibility = View.GONE
    }

    private fun showHistoryTab() {
        binding.contentOverview.visibility = View.GONE
        binding.contentChapters.visibility = View.GONE
        binding.contentAchievements.visibility = View.GONE
        binding.contentHistory.visibility = View.VISIBLE
    }

    companion object {
        const val EXTRA_STATISTICS_TYPE = "extra_statistics_type"
    }
}
