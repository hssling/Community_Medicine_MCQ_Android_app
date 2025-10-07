package com.supernova.communitymedicine.ui.chapters

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.supernova.communitymedicine.R
import com.supernova.communitymedicine.ui.quiz.QuizActivity
import com.supernova.communitymedicine.viewmodel.ChaptersViewModel
import kotlinx.coroutines.launch

class ChaptersActivity : AppCompatActivity() {

    private val viewModel: ChaptersViewModel by viewModels()
    private lateinit var chaptersAdapter: ChaptersAdapter

    // UI elements
    private lateinit var recyclerChapters: androidx.recyclerview.widget.RecyclerView
    private lateinit var swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    private lateinit var tvEmptyState: TextView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapters)

        setupUI()
        observeViewModel()
        loadChapters()
    }

    private fun setupUI() {
        // Initialize UI elements
        recyclerChapters = findViewById(R.id.recycler_chapters)
        swipeRefresh = findViewById(R.id.swipe_refresh)
        tvEmptyState = findViewById(R.id.tv_empty_state)
        toolbar = findViewById(R.id.toolbar)
        progressBar = findViewById(R.id.progress_bar)

        // Set up toolbar
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Set up RecyclerView
        chaptersAdapter = ChaptersAdapter { chapter ->
            showChapterOptionsDialog(chapter)
        }

        recyclerChapters.apply {
            layoutManager = LinearLayoutManager(this@ChaptersActivity)
            adapter = chaptersAdapter
        }

        // Set up refresh layout
        swipeRefresh.setOnRefreshListener {
            loadChapters()
        }
    }

    private fun observeViewModel() {
        viewModel.chapters.observe(this) { chapters ->
            chaptersAdapter.submitList(chapters)
            tvEmptyState.visibility = if (chapters.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            swipeRefresh.isRefreshing = isLoading
            progressBar.visibility = if (isLoading && !swipeRefresh.isRefreshing) View.VISIBLE else View.GONE
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
    }

    private fun loadChapters() {
        lifecycleScope.launch {
            try {
                viewModel.loadChapters()
            } catch (e: Exception) {
                androidx.appcompat.app.AlertDialog.Builder(this@ChaptersActivity)
                    .setTitle("Error")
                    .setMessage("Error loading chapters: ${e.message}")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    private fun showChapterOptionsDialog(chapter: com.supernova.communitymedicine.data.model.ChapterProgress) {
        val options = arrayOf(
            "Start Quiz (10 Questions)",
            "Start Quiz (20 Questions)",
            "Start Quiz (30 Questions)",
            "Practice Weak Areas",
            "View Statistics"
        )

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Choose ${chapter.chapterName}")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> startQuiz(chapter.chapterName, "Mixed", 10)
                    1 -> startQuiz(chapter.chapterName, "Mixed", 20)
                    2 -> startQuiz(chapter.chapterName, "Mixed", 30)
                    3 -> startQuiz(chapter.chapterName, "Easy", 15) // Focus on weak areas
                    4 -> showChapterStatistics(chapter)
                }
            }
            .show()
    }

    private fun startQuiz(chapter: String, difficulty: String, questionCount: Int) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(QuizActivity.EXTRA_CHAPTER, chapter)
            putExtra(QuizActivity.EXTRA_DIFFICULTY, difficulty)
            putExtra(QuizActivity.EXTRA_QUESTION_COUNT, questionCount)
        }
        startActivity(intent)
    }

    private fun showChapterStatistics(chapter: com.supernova.communitymedicine.data.model.ChapterProgress) {
        val message = buildString {
            append("Chapter: ${chapter.chapterName}\n\n")
            append("Progress: ${chapter.getProgressPercentage().toInt()}%\n")
            append("Accuracy: ${chapter.getAccuracy().toInt()}%\n")
            append("Questions Attempted: ${chapter.questionsAttempted}/${chapter.totalQuestions}\n")
            append("Best Score: ${chapter.bestScore.toInt()}%\n")
            append("Quizzes Completed: ${chapter.quizzesCompleted}\n")
            append("Study Streak: ${chapter.currentStreak} days\n")
            append("Mastery Level: ${chapter.getMasteryLevel()}")
        }

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Chapter Statistics")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    companion object {
        const val EXTRA_CHAPTER = "extra_chapter"
    }
}
