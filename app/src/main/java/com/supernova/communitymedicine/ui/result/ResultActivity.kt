package com.supernova.communitymedicine.ui.result

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

import com.supernova.communitymedicine.ui.chapters.ChaptersActivity
import com.supernova.communitymedicine.ui.quiz.QuizActivity
import com.supernova.communitymedicine.viewmodel.ResultViewModel
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    private val viewModel: ResultViewModel by viewModels()

    // UI elements
    private lateinit var tvChapterName: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvMotivationalMessage: TextView
    private lateinit var tvCorrectAnswers: TextView
    private lateinit var tvWrongAnswers: TextView
    private lateinit var tvSkippedQuestions: TextView
    private lateinit var tvTimeTaken: TextView
    private lateinit var tvAccuracy: TextView
    private lateinit var progressOverall: ProgressBar
    private lateinit var progressAccuracy: ProgressBar
    private lateinit var btnRetakeQuiz: MaterialButton
    private lateinit var btnBackToChapters: MaterialButton
    private lateinit var btnReviewAnswers: MaterialButton
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Get quiz results from intent
        val quizResult = intent.getParcelableExtra<com.supernova.communitymedicine.data.model.QuizResult>(EXTRA_QUIZ_RESULTS)
        val chapter = intent.getStringExtra(EXTRA_CHAPTER) ?: ""

        if (quizResult == null) {
            finish()
            return
        }

        setupUI()
        observeViewModel()
        displayResults(quizResult, chapter)
    }

    private fun setupUI() {
        // Initialize UI elements
        tvChapterName = findViewById(R.id.tv_chapter_name)
        tvScore = findViewById(R.id.tv_score)
        tvMotivationalMessage = findViewById(R.id.tv_motivational_message)
        tvCorrectAnswers = findViewById(R.id.tv_correct_answers)
        tvWrongAnswers = findViewById(R.id.tv_wrong_answers)
        tvSkippedQuestions = findViewById(R.id.tv_skipped_questions)
        tvTimeTaken = findViewById(R.id.tv_time_taken)
        tvAccuracy = findViewById(R.id.tv_accuracy)
        progressOverall = findViewById(R.id.progress_overall)
        progressAccuracy = findViewById(R.id.progress_accuracy)
        btnRetakeQuiz = findViewById(R.id.btn_retake_quiz)
        btnBackToChapters = findViewById(R.id.btn_back_to_chapters)
        btnReviewAnswers = findViewById(R.id.btn_review_answers)
        toolbar = findViewById(R.id.toolbar)
        progressBar = findViewById(R.id.progress_bar)

        // Set up click listeners
        btnRetakeQuiz.setOnClickListener {
            retakeQuiz()
        }

        btnBackToChapters.setOnClickListener {
            navigateToChapters()
        }

        btnReviewAnswers.setOnClickListener {
            showDetailedReview()
        }

        // Set up toolbar
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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

    private fun displayResults(quizResult: com.supernova.communitymedicine.data.model.QuizResult, chapter: String) {
        // Update toolbar title
        toolbar.title = getString(R.string.quiz_results)

        // Display chapter name
        tvChapterName.text = chapter

        // Display score with animation
        displayScore(quizResult.percentage)

        // Display statistics
        tvCorrectAnswers.text = getString(
            R.string.correct_answers_count,
            quizResult.correctAnswers,
            quizResult.totalQuestions
        )

        tvWrongAnswers.text = getString(
            R.string.wrong_answers_count,
            quizResult.wrongAnswers
        )

        tvSkippedQuestions.text = getString(
            R.string.skipped_questions_count,
            quizResult.skippedQuestions
        )

        tvTimeTaken.text = getString(
            R.string.time_taken_format,
            formatTime(quizResult.timeTaken)
        )

        tvAccuracy.text = getString(
            R.string.accuracy_format,
            quizResult.percentage
        )

        // Show motivational message
        showMotivationalMessage(quizResult.percentage)

        // Update progress bars
        updateProgressBars(quizResult)
    }

    private fun displayScore(score: Float) {
        tvScore.text = getString(R.string.score_percentage, score.toInt())

        // Set score color based on performance
        val color = when {
            score >= 90 -> R.color.progress_excellent
            score >= 75 -> R.color.progress_good
            score >= 60 -> R.color.progress_average
            else -> R.color.progress_poor
        }
        tvScore.setTextColor(getColor(color))
    }

    private fun updateProgressBars(quizResult: com.supernova.communitymedicine.data.model.QuizResult) {
        // Overall score progress
        progressOverall.progress = quizResult.percentage.toInt()
        progressOverall.progressTintList = androidx.core.content.ContextCompat.getColorStateList(
            this,
            when {
                quizResult.percentage >= 90 -> R.color.progress_excellent
                quizResult.percentage >= 75 -> R.color.progress_good
                quizResult.percentage >= 60 -> R.color.progress_average
                else -> R.color.progress_poor
            }
        )

        // Accuracy progress (same as overall for now)
        progressAccuracy.progress = quizResult.percentage.toInt()
        progressAccuracy.progressTintList = androidx.core.content.ContextCompat.getColorStateList(
            this,
            when {
                quizResult.percentage >= 90 -> R.color.progress_excellent
                quizResult.percentage >= 75 -> R.color.progress_good
                quizResult.percentage >= 60 -> R.color.progress_average
                else -> R.color.progress_poor
            }
        )
    }

    private fun showMotivationalMessage(score: Float) {
        val message = when {
            score >= 90 -> getString(R.string.excellent_work)
            score >= 75 -> getString(R.string.great_job)
            score >= 60 -> getString(R.string.good_effort)
            else -> getString(R.string.keep_practicing)
        }

        tvMotivationalMessage.text = message
        tvMotivationalMessage.visibility = View.VISIBLE
    }

    private fun formatTime(seconds: Long): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    private fun retakeQuiz() {
        val chapter = intent.getStringExtra(EXTRA_CHAPTER) ?: ""
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(QuizActivity.EXTRA_CHAPTER, chapter)
            putExtra(QuizActivity.EXTRA_DIFFICULTY, "Mixed")
            putExtra(QuizActivity.EXTRA_QUESTION_COUNT, 10)
        }
        startActivity(intent)
        finish()
    }

    private fun navigateToChapters() {
        val intent = Intent(this, ChaptersActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showDetailedReview() {
        // For now, show a simple dialog
        // In a full implementation, this would show a detailed review screen
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Detailed Review")
            .setMessage("Detailed answer review feature will be available in the next update!")
            .setPositiveButton("OK", null)
            .show()
    }

    companion object {
        const val EXTRA_QUIZ_RESULTS = "extra_quiz_results"
        const val EXTRA_CHAPTER = "extra_chapter"
    }
}
