import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.supernova.communitymedicine.R
import com.supernova.communitymedicine.data.model.Bookmark
import com.supernova.communitymedicine.databinding.ItemBookmarkBinding

class BookmarksAdapter(
    private val onBookmarkClick: (Bookmark) -> Unit,
    private val onBookmarkLongClick: (Bookmark) -> Unit
) : ListAdapter<Bookmark, BookmarksAdapter.BookmarkViewHolder>(BookmarkDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemBookmarkBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BookmarkViewHolder(binding, onBookmarkClick, onBookmarkLongClick)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = getItem(position)
        holder.bind(bookmark)
    }

    class BookmarkViewHolder(
        private val binding: ItemBookmarkBinding,
        private val onBookmarkClick: (Bookmark) -> Unit,
        private val onBookmarkLongClick: (Bookmark) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookmark: Bookmark) {
            binding.tvQuestionText.text = if (bookmark.questionText.length > 100)
                bookmark.questionText.substring(0, 97) + "..."
            else bookmark.questionText
            binding.tvChapter.text = bookmark.chapter
            binding.tvDifficulty.text = bookmark.difficulty
            binding.tvReviewCount.text = bookmark.getReviewFrequencyText()
            binding.tvTimeAgo.text = bookmark.getTimeAgo()

            // Display tag if available
            if (!bookmark.tag.isNullOrEmpty()) {
                binding.tvTag.text = bookmark.tag
                binding.tvTag.visibility = ViewGroup.VISIBLE
                binding.tvTag.backgroundTintList = ContextCompat.getColorStateList(
                    binding.root.context,
                    getTagColor(bookmark.tag)
                )
            } else {
                binding.tvTag.visibility = ViewGroup.GONE
            }

            // Display note preview if available
            if (!bookmark.note.isNullOrEmpty()) {
                binding.tvNotePreview.text = if (bookmark.note.length > 50)
                    bookmark.note.substring(0, 47) + "..."
                else bookmark.note
                binding.tvNotePreview.visibility = ViewGroup.VISIBLE
            } else {
                binding.tvNotePreview.visibility = ViewGroup.GONE
            }

            // Set difficulty color
            val difficultyColor = when (bookmark.difficulty.lowercase()) {
                "easy" -> R.color.difficulty_easy
                "medium" -> R.color.difficulty_medium
                "hard" -> R.color.difficulty_hard
                else -> R.color.secondary_text
            }
            binding.tvDifficulty.setTextColor(ContextCompat.getColor(binding.root.context, difficultyColor))

            // Set click listeners
            binding.root.setOnClickListener { onBookmarkClick(bookmark) }
            binding.root.setOnLongClickListener {
                onBookmarkLongClick(bookmark)
                true
            }
        }

        private fun getTagColor(tag: String): Int {
            return when (tag.lowercase()) {
                "important" -> R.color.tag_important
                "weak" -> R.color.progress_poor
                "need review" -> R.color.tag_review
                "exam" -> R.color.tag_exam
                else -> R.color.primary
            }
        }
    }

    class BookmarkDiffCallback : DiffUtil.ItemCallback<Bookmark>() {
        override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
            return oldItem == newItem
        }
    }

    private fun String.truncate(maxLength: Int): String {
        return if (this.length <= maxLength) this else this.substring(0, maxLength) + "..."
    }
}
