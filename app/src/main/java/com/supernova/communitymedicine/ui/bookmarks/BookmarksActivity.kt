package com.supernova.communitymedicine.ui.bookmarks

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.supernova.communitymedicine.R
import com.supernova.communitymedicine.data.model.Bookmark
import com.supernova.communitymedicine.databinding.ActivityBookmarksBinding
import com.supernova.communitymedicine.ui.bookmarks.BookmarksAdapter
import com.supernova.communitymedicine.ui.quiz.QuizActivity
import com.supernova.communitymedicine.viewmodel.BookmarksViewModel
import kotlinx.coroutines.launch
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class BookmarksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarksBinding
    private val viewModel: BookmarksViewModel by viewModels()
    private lateinit var bookmarksAdapter: BookmarksAdapter
    private val tags = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
        loadBookmarks()
    }

    private fun setupUI() {
        // Set up toolbar
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Set up RecyclerView
        bookmarksAdapter = BookmarksAdapter(
            onBookmarkClick = { bookmark -> showBookmarkOptionsDialog(bookmark) },
            onBookmarkLongClick = { bookmark -> showEditBookmarkDialog(bookmark) }
        )
        binding.recyclerBookmarks.apply {
            layoutManager = LinearLayoutManager(this@BookmarksActivity)
            adapter = bookmarksAdapter
        }

        // Set up search functionality
        binding.etSearch.setOnClickListener {
            // TODO: Implement search functionality
        }

        // Set up filter chips
        setupFilterChips()

        // Set up refresh layout
        binding.swipeRefresh.setOnRefreshListener {
            loadBookmarks()
        }

        // Set up empty state
        binding.btnCreateBookmark.setOnClickListener {
            // Navigate to chapters to create new quiz session
            startActivity(Intent(this, com.supernova.communitymedicine.ui.chapters.ChaptersActivity::class.java))
        }
    }

    private fun setupFilterChips() {
        binding.chipGroup.addView(createChip("All", true) {
            bookmarksAdapter.submitList(viewModel.bookmarks.value)
        })

        // Add difficulty chips
        listOf("Easy", "Medium", "Hard").forEach { difficulty ->
            binding.chipGroup.addView(createChip(difficulty, false) {
                filterBookmarks(difficulty = difficulty.lowercase())
            })
        }

        // Add tag chips (will be populated dynamically)
        viewModel.bookmarks.observe(this) { bookmarks ->
            val newTags = bookmarks.mapNotNull { it.tag }.distinct()
            if (newTags != tags) {
                tags.clear()
                tags.addAll(newTags)

                // Remove old tag chips and add new ones
                binding.chipGroup.removeViews(4, binding.chipGroup.childCount - 4) // Remove tag chips
                tags.forEach { tag ->
                    binding.chipGroup.addView(createChip(tag, false) {
                        filterBookmarks(tag = tag)
                    })
                }
            }
        }
    }

    private fun createChip(text: String, checked: Boolean, onClick: () -> Unit): Chip {
        return Chip(this).apply {
            this.text = text
            isChecked = checked
            isCheckable = true
            setOnClickListener {
                // Clear other checked chips
                for (i in 0 until binding.chipGroup.childCount) {
                    (binding.chipGroup.getChildAt(i) as? Chip)?.isChecked = false
                }
                isChecked = true
                onClick()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
            binding.progressBar.visibility = if (isLoading && !binding.swipeRefresh.isRefreshing) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.bookmarks.observe(this) { bookmarks ->
            val filteredBookmarks = viewModel.filteredBookmarks.value ?: bookmarks
            bookmarksAdapter.submitList(filteredBookmarks)
            updateEmptyState(filteredBookmarks.isEmpty())
            binding.tvBookmarksCount.text = "${filteredBookmarks.size} bookmarks"
        }

        viewModel.filteredBookmarks.observe(this) { filteredBookmarks ->
            bookmarksAdapter.submitList(filteredBookmarks)
            updateEmptyState(filteredBookmarks.isEmpty())
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerBookmarks.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun loadBookmarks() {
        lifecycleScope.launch {
            try {
                viewModel.loadBookmarks()
            } catch (e: Exception) {
                Toast.makeText(this@BookmarksActivity, "Error loading bookmarks: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showBookmarkOptionsDialog(bookmark: Bookmark) {
        val options = arrayOf(
            "Review Question",
            "Practice Similar Questions",
            "Edit Note",
            "Remove Bookmark"
        )

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Question Bookmark")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> reviewBookmark(bookmark)
                    1 -> practiceSimilarQuestions(bookmark)
                    2 -> showEditNoteDialog(bookmark)
                    3 -> removeBookmark(bookmark)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun reviewBookmark(bookmark: Bookmark) {
        // Create a simple review dialog
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_bookmark_review)

        val tvQuestion = dialog.findViewById<TextView>(R.id.tv_question)
        val radioGroup = dialog.findViewById<RadioGroup>(R.id.radio_group_options)
        val tvCorrectAnswer = dialog.findViewById<TextView>(R.id.tv_correct_answer)
        val btnClose = dialog.findViewById<Button>(R.id.btn_close)

        tvQuestion.text = bookmark.questionText

        // Add options to radio group
        bookmark.questionOptions.forEach { option ->
            val radioButton = RadioButton(this).apply {
                text = option
                if (option == bookmark.correctAnswer) {
                    setTextColor(getColor(android.R.color.holo_green_dark))
                    text = "$option ✓"
                }
            }
            radioGroup.addView(radioButton)
        }

        tvCorrectAnswer.text = "Correct Answer: ${bookmark.correctAnswer}"

        btnClose.setOnClickListener { dialog.dismiss() }

        dialog.show()

        // Mark as reviewed
        viewModel.markBookmarkReviewed(bookmark)
    }

    private fun practiceSimilarQuestions(bookmark: Bookmark) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra(QuizActivity.EXTRA_CHAPTER, bookmark.chapter)
            putExtra(QuizActivity.EXTRA_DIFFICULTY, bookmark.difficulty)
            putExtra(QuizActivity.EXTRA_QUESTION_COUNT, 10)
        }
        startActivity(intent)
    }

    private fun showEditNoteDialog(bookmark: Bookmark) {
        val editText = EditText(this).apply {
            setText(bookmark.note ?: "")
            hint = "Add a note..."
        }

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Edit Note")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                viewModel.updateBookmarkNote(bookmark, editText.text.toString())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditBookmarkDialog(bookmark: Bookmark) {
        val view = layoutInflater.inflate(R.layout.dialog_edit_bookmark, null)
        val etTag = view.findViewById<EditText>(R.id.et_tag)
        val etNote = view.findViewById<EditText>(R.id.et_note)

        etTag.setText(bookmark.tag ?: "")
        etNote.setText(bookmark.note ?: "")

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Edit Bookmark")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                viewModel.updateBookmark(
                    bookmark.copy(
                        tag = etTag.text.toString().takeIf { it.isNotEmpty() },
                        note = etNote.text.toString().takeIf { it.isNotEmpty() }
                    )
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun removeBookmark(bookmark: Bookmark) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Remove Bookmark")
            .setMessage("Are you sure you want to remove this bookmark?")
            .setPositiveButton("Remove") { _, _ ->
                viewModel.removeBookmark(bookmark)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun filterBookmarks(difficulty: String? = null, tag: String? = null) {
        viewModel.filterBookmarks(difficulty, tag)
    }
}
