package com.supernova.communitymedicine

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.supernova.communitymedicine.ui.bookmarks.BookmarksActivity
import com.supernova.communitymedicine.ui.chapters.ChaptersActivity
import com.supernova.communitymedicine.ui.quiz.QuizActivity
import com.supernova.communitymedicine.ui.result.ResultActivity
import com.supernova.communitymedicine.ui.statistics.StatisticsActivity
import com.supernova.communitymedicine.utils.DatabaseSeeder
import com.supernova.communitymedicine.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    // UI elements
    private lateinit var progressOverall: ProgressBar
    private lateinit var tvOverallProgress: TextView
    private lateinit var tvTotalQuestions: TextView
    private lateinit var tvCorrectAnswers: TextView
    private lateinit var tvStudyStreak: TextView
    private lateinit var btnStartQuiz: MaterialButton
    private lateinit var btnBookmarks: MaterialButton
    private lateinit var btnStats: MaterialButton
    private lateinit var btnSettings: MaterialButton
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        observeViewModel()
        loadInitialData()
    }

    private fun setupUI() {
        // Initialize UI elements
        progressOverall = findViewById(R.id.progress_overall)
        tvOverallProgress = findViewById(R.id.tv_overall_progress)
        tvTotalQuestions = findViewById(R.id.tv_total_questions)
        tvCorrectAnswers = findViewById(R.id.tv_correct_answers)
        tvStudyStreak = findViewById(R.id.tv_study_streak)
        btnStartQuiz = findViewById(R.id.btn_start_quiz)
        btnBookmarks = findViewById(R.id.btn_bookmarks)
        btnStats = findViewById(R.id.btn_stats)
        btnSettings = findViewById(R.id.btn_settings)
        progressBar = findViewById(R.id.progress_bar)

        // Set up click listeners
        btnStartQuiz.setOnClickListener {
            startActivity(Intent(this, ChaptersActivity::class.java))
        }

        btnBookmarks.setOnClickListener {
            showBookmarks()
        }

        btnStats.setOnClickListener {
            showStatistics()
        }

        btnSettings.setOnClickListener {
            showSettings()
        }

        // Set up progress indicators
        setupProgressIndicators()
    }

    private fun setupProgressIndicators() {
        lifecycleScope.launch {
            viewModel.getOverallProgress().collect { progress ->
                progressOverall.progress = progress.completionPercentage.toInt()
                tvOverallProgress.text = "${progress.completionPercentage.toInt()}% Complete"

                tvTotalQuestions.text = "Questions: ${progress.totalQuestions}"
                tvCorrectAnswers.text = "Correct: ${progress.correctAnswers}"
                tvStudyStreak.text = "Streak: ${progress.currentStreak} days"
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadInitialData() {
        lifecycleScope.launch {
            try {
                // Seed database with MCQ questions on first launch
                val databaseSeeder = DatabaseSeeder(this@MainActivity)
                databaseSeeder.seedDatabaseIfNeeded()

                // Load initial app data
                viewModel.loadInitialData()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity,
                    "Error loading data: ${e.message}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showBookmarks() {
        // Navigate to bookmarks screen
        startActivity(Intent(this, BookmarksActivity::class.java))
    }

    private fun showStatistics() {
        // Navigate to statistics screen
        startActivity(Intent(this, StatisticsActivity::class.java))
    }

    private fun showSettings() {
        // Navigate to settings screen
        Toast.makeText(this, "Settings feature coming soon!", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        // Refresh data when returning to main screen
        loadInitialData()
    }
}
