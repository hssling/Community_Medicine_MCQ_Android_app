package com.supernova.communitymedicine.ui.quiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.supernova.communitymedicine.R
import com.supernova.communitymedicine.databinding.ActivityQuizBinding
import com.supernova.communitymedicine.ui.result.ResultActivity
import com.supernova.communitymedicine.viewmodel.QuizViewModel
import kotlinx.coroutines.launch

class QuizActivity : AppCompatActivity() {

    private val viewModel: QuizViewModel by viewModels()

    private var questionTimer: CountDownTimer? = null
    private var timeRemaining: Long = 0

    // UI elements
    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvQuestionText: TextView
    private lateinit var tvTimer: TextView
    private lateinit var btnOptionA: MaterialRadioButton
    private lateinit var btnOptionB: MaterialRadioButton
    private lateinit var btnOptionC: MaterialRadioButton
    private lateinit var btnOptionD: MaterialRadioButton
    private lateinit var btnNext: MaterialButton
    private lateinit var btnPrevious: MaterialButton
    private lateinit var btnBookmark: MaterialButton
    private lateinit var btnSubmit: MaterialButton
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Get chapter and difficulty from intent
        val chapter = intent.getStringExtra(EXTRA_CHAPTER) ?: "Concept of Health and Disease"
        val difficulty = intent.getStringExtra(EXTRA_DIFFICULTY) ?: "Mixed"
        val questionCount = intent.getIntExtra(EXTRA_QUESTION_COUNT, 10)

        setupUI()
        observeViewModel()
        startQuiz(chapter, difficulty, questionCount)
    }

    private fun setupUI() {
        // Initialize UI elements
        tvQuestionNumber = findViewById(R.id.tv_question_number)
        tvQuestionText = findViewById(R.id.tv_question_text)
        tvTimer = findViewById(R.id.tv_timer)
        btnOptionA = findViewById(R.id.btn_option_a)
        btnOptionB = findViewById(R.id.btn_option_b)
        btnOptionC = findViewById(R.id.btn_option_c)
        btnOptionD = findViewById(R.id.btn_option_d)
        btnNext = findViewById(R.id.btn_next)
        btnPrevious = findViewById(R.id.btn_previous)
        btnBookmark = findViewById(R.id.btn_bookmark)
        btnSubmit = findViewById(R.id.btn_submit)
        toolbar = findViewById(R.id.toolbar)
        progressBar = findViewById(R.id.progress_bar)

        // Set up click listeners
        btnOptionA.setOnClickListener { selectAnswer("A") }
        btnOptionB.setOnClickListener { selectAnswer("B") }
        btnOptionC.setOnClickListener { selectAnswer("C") }
        btnOptionD.setOnClickListener { selectAnswer("D") }

        btnNext.setOnClickListener { viewModel.nextQuestion() }
        btnPrevious.setOnClickListener { viewModel.previousQuestion() }
        btnBookmark.setOnClickListener { viewModel.toggleBookmark() }
        btnSubmit.setOnClickListener { submitAnswer() }

        // Set up back button
        toolbar.setNavigationOnClickListener {
            showExitConfirmationDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.currentQuestion.observe(this) { question ->
            question?.let { displayQuestion(it) }
        }

        viewModel.currentQuestionIndex.observe(this) { index ->
            tvQuestionNumber.text = getString(
                R.string.question_number,
                index + 1,
                viewModel.totalQuestions
            )
        }

        viewModel.selectedAnswer.observe(this) { selected ->
            updateOptionButtonStates(selected)
        }

        viewModel.isLastQuestion.observe(this) { isLast ->
            btnNext.text = if (isLast) getString(R.string.finish_quiz) else getString(R.string.next)
        }

        viewModel.quizCompleted.observe(this) { completed ->
            if (completed) {
                finishQuiz()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.timeRemaining.observe(this) { time ->
            timeRemaining = time
            updateTimerDisplay(time)
        }
    }

    private fun displayQuestion(question: com.supernova.communitymedicine.data.model.Question) {
        tvQuestionText.text = question.questionText
        btnOptionA.text = "A. ${question.optionA}"
        btnOptionB.text = "B. ${question.optionB}"
        btnOptionC.text = "C. ${question.optionC}"
        btnOptionD.text = "D. ${question.optionD}"

        // Update bookmark button state
        btnBookmark.setIconResource(
            if (question.isBookmarked) R.drawable.ic_bookmark_filled
            else R.drawable.ic_bookmark_border
        )

        // Clear previous selection
        viewModel.clearSelection()

        // Start timer for this question
        startQuestionTimer(question.timeLimit * 1000L)
    }

    private fun selectAnswer(answer: String) {
        viewModel.selectAnswer(answer)
        stopQuestionTimer()
    }

    private fun submitAnswer() {
        val selectedAnswer = viewModel.selectedAnswer.value
        if (selectedAnswer.isNullOrEmpty()) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.submitAnswer()
    }

    private fun updateOptionButtonStates(selectedAnswer: String?) {
        val buttons = listOf(
            btnOptionA to "A",
            btnOptionB to "B",
            btnOptionC to "C",
            btnOptionD to "D"
        )

        buttons.forEach { (button, option) ->
            val isSelected = selectedAnswer == option
            button.isChecked = isSelected

            // Update colors based on selection state
            val color = when {
                isSelected -> R.color.selected_answer
                else -> R.color.surface
            }
            button.setBackgroundColor(getColor(color))
        }
    }

    private fun updateTimerDisplay(timeInMillis: Long) {
        val seconds = (timeInMillis / 1000).toInt()
        tvTimer.text = String.format("%02d:%02d", seconds / 60, seconds % 60)

        // Update timer color based on remaining time
        val color = when {
            seconds <= 10 -> R.color.wrong_answer // Red for urgent
            seconds <= 30 -> R.color.progress_average // Orange for warning
            else -> R.color.primary // Normal color
        }
        tvTimer.setTextColor(getColor(color))
    }

    private fun startQuestionTimer(durationInMillis: Long) {
        stopQuestionTimer()

        questionTimer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewModel.updateTimeRemaining(millisUntilFinished)
            }

            override fun onFinish() {
                // Auto-submit with current selection or mark as skipped
                if (viewModel.selectedAnswer.value.isNullOrEmpty()) {
                    viewModel.skipQuestion()
                } else {
                    viewModel.submitAnswer()
                }
            }
        }.start()
    }

    private fun stopQuestionTimer() {
        questionTimer?.cancel()
        questionTimer = null
    }

    private fun startQuiz(chapter: String, difficulty: String, questionCount: Int) {
        lifecycleScope.launch {
            try {
                viewModel.startQuiz(chapter, difficulty, questionCount)
            } catch (e: Exception) {
                Toast.makeText(this@QuizActivity,
                    "Error starting quiz: ${e.message}",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun finishQuiz() {
        stopQuestionTimer()

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(ResultActivity.EXTRA_QUIZ_RESULTS, viewModel.getQuizResults())
            putExtra(ResultActivity.EXTRA_CHAPTER, viewModel.chapter)
        }
        startActivity(intent)
        finish()
    }

    private fun showExitConfirmationDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Exit Quiz")
            .setMessage("Are you sure you want to exit? Your progress will be lost.")
            .setPositiveButton("Exit") { _, _ ->
                stopQuestionTimer()
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopQuestionTimer()
    }

    companion object {
        const val EXTRA_CHAPTER = "extra_chapter"
        const val EXTRA_DIFFICULTY = "extra_difficulty"
        const val EXTRA_QUESTION_COUNT = "extra_question_count"
    }
}
