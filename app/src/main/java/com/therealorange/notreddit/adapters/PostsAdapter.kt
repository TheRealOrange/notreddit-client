package com.therealorange.notreddit.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import com.therealorange.notreddit.R
import com.therealorange.notreddit.client.data.VoteStatus
import com.therealorange.notreddit.util.DownloadImageTask
import com.therealorange.notreddit.util.PostItem
import kotlinx.android.synthetic.main.header_subnotreddit.view.*
import kotlinx.android.synthetic.main.img_content.view.*
import kotlinx.android.synthetic.main.post_footer.view.*
import kotlinx.android.synthetic.main.text_content.view.*
import kotlinx.android.synthetic.main.title_content.view.*


class PostsAdapter(private var context: Context, private var items: List<PostItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_TEXT = 1
        private const val TYPE_IMG = 2
        private const val TYPE_IMGTEXT = 3
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TEXT->TextPostViewHolder(LayoutInflater.from(context).inflate(R.layout.card_text_post, parent, false))
            TYPE_IMG->ImgPostViewHolder(LayoutInflater.from(context).inflate(R.layout.card_img_post, parent, false))
            TYPE_IMGTEXT->ImgTextPostViewHolder(LayoutInflater.from(context).inflate(R.layout.card_imgtext_post, parent, false))
            else->ImgTextPostViewHolder(LayoutInflater.from(context).inflate(R.layout.card_imgtext_post, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_TEXT->(holder as TextPostViewHolder).bind(items[position] as PostItem.TextPostItem)
            TYPE_IMG->(holder as ImgPostViewHolder).bind(items[position] as PostItem.ImgPostItem)
            TYPE_IMGTEXT->(holder as ImgTextPostViewHolder).bind(items[position] as PostItem.ImgTextPostItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PostItem.TextPostItem -> TYPE_TEXT
            is PostItem.ImgPostItem -> TYPE_IMG
            is PostItem.ImgTextPostItem -> TYPE_IMGTEXT
        }
    }

    fun addItem(p: PostItem) {
        val temp = items.toMutableList()
        temp.add(p)
        items = temp.toList()
        notifyDataSetChanged()
    }

    fun updateItem(p: PostItem, position: Int) {
        val temp = items.toMutableList()
        temp[position] = p
        items = temp.toList()
        notifyItemChanged(position)
    }

    class TextPostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(p: PostItem.TextPostItem) {
            with (view) {
                DownloadImageTask(subNotRedditIcon).execute(p.content.subnotredddit.icon)
                subNotRedditName.text = p.content.subnotredddit.name
                postUserName.text = p.content.user.username
                postTime.text = p.content.time
                postTitle.text = p.content.title
                postText.text = p.content.text
                score.text = p.content.score.toString()
                commentButton.text = p.content.comments.toString()

                subNotRedditIcon.setOnClickListener { p.subNotRedditClicked.invoke(p) }
                subNotRedditName.setOnClickListener { p.subNotRedditClicked.invoke(p) }

                postUserName.setOnClickListener { p.userClicked.invoke(p) }

                postTitle.setOnClickListener { p.titleClicked.invoke(p) }
                postText.setOnClickListener { p.textClicked.invoke(p) }

                upvoteButton.setOnClickListener { p.upvoteClicked.invoke(upvoteButton, p) }
                downvoteButton.setOnClickListener { p.downvoteClicked.invoke(downvoteButton, p) }

                commentButton.setOnClickListener { p.commentClicked.invoke(p) }
                shareButton.setOnClickListener { p.shareClicked.invoke(p) }

                val upvotetypedValue = TypedValue()
                val downvotetypedValue = TypedValue()
                val neutralvotetypedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(R.attr.colorUpvote, upvotetypedValue, true)
                theme.resolveAttribute(R.attr.colorDownvote, downvotetypedValue, true)
                theme.resolveAttribute(R.attr.colorTextAccent, neutralvotetypedValue, true)
                @ColorInt val upvotecolor = upvotetypedValue.data
                @ColorInt val downvotecolor = downvotetypedValue.data
                @ColorInt val neutralcolor = neutralvotetypedValue.data

                when(p.content.voted) {
                    VoteStatus.UPVOTE -> {
                        upvoteButton.iconTint = context.getColorStateList(upvotecolor)
                        downvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        score.setTextColor(upvotecolor)
                    }
                    VoteStatus.DOWNVOTE -> {
                        upvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        downvoteButton.iconTint = context.getColorStateList(downvotecolor)
                        score.setTextColor(downvotecolor)
                    }
                    VoteStatus.NONE -> {
                        upvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        downvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        score.setTextColor(neutralcolor)
                    }
                    else -> {}
                }
            }
        }
    }


    class ImgPostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(p: PostItem.ImgPostItem) {
            with (view) {
                DownloadImageTask(subNotRedditIcon).execute(p.content.subnotredddit.icon)
                subNotRedditName.text = p.content.subnotredddit.name
                postUserName.text = p.content.user.username
                postTime.text = p.content.time
                postTitle.text = p.content.title
                DownloadImageTask(postImg).execute(p.content.imgs[0])
                score.text = p.content.score.toString()
                commentButton.text = p.content.comments.toString()

                subNotRedditIcon.setOnClickListener { p.subNotRedditClicked.invoke(p) }
                subNotRedditName.setOnClickListener { p.subNotRedditClicked.invoke(p) }

                postUserName.setOnClickListener { p.userClicked.invoke(p) }

                postTitle.setOnClickListener { p.titleClicked.invoke(p) }
                postImg.setOnClickListener { p.imageClicked.invoke(p) }

                upvoteButton.setOnClickListener { p.upvoteClicked.invoke(upvoteButton, p) }
                downvoteButton.setOnClickListener { p.downvoteClicked.invoke(downvoteButton, p) }

                commentButton.setOnClickListener { p.commentClicked.invoke(p) }
                shareButton.setOnClickListener { p.shareClicked.invoke(p) }

                val upvotetypedValue = TypedValue()
                val downvotetypedValue = TypedValue()
                val neutralvotetypedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(R.attr.colorUpvote, upvotetypedValue, true)
                theme.resolveAttribute(R.attr.colorDownvote, downvotetypedValue, true)
                theme.resolveAttribute(R.attr.colorTextAccent, neutralvotetypedValue, true)
                @ColorInt val upvotecolor = upvotetypedValue.data
                @ColorInt val downvotecolor = downvotetypedValue.data
                @ColorInt val neutralcolor = neutralvotetypedValue.data

                when(p.content.voted) {
                    VoteStatus.UPVOTE -> {
                        upvoteButton.iconTint = context.getColorStateList(upvotecolor)
                        downvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        score.setTextColor(upvotecolor)
                    }
                    VoteStatus.DOWNVOTE -> {
                        upvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        downvoteButton.iconTint = context.getColorStateList(downvotecolor)
                        score.setTextColor(downvotecolor)
                    }
                    VoteStatus.NONE -> {
                        upvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        downvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        score.setTextColor(neutralcolor)
                    }
                    else -> {}
                }
            }
        }
    }

    class ImgTextPostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(p: PostItem.ImgTextPostItem) {
            with (view) {
                DownloadImageTask(subNotRedditIcon).execute(p.content.subnotredddit.icon)
                subNotRedditName.text = p.content.subnotredddit.name
                postUserName.text = p.content.user.username
                postTime.text = p.content.time
                postTitle.text = p.content.title
                postText.text = p.content.text
                DownloadImageTask(postImg).execute(p.content.imgs[0])
                score.text = p.content.score.toString()
                commentButton.text = p.content.comments.toString()

                subNotRedditIcon.setOnClickListener { p.subNotRedditClicked.invoke(p) }
                subNotRedditName.setOnClickListener { p.subNotRedditClicked.invoke(p) }

                postUserName.setOnClickListener { p.userClicked.invoke(p) }

                postTitle.setOnClickListener { p.titleClicked.invoke(p) }
                postText.setOnClickListener { p.textClicked.invoke(p) }
                postImg.setOnClickListener { p.imageClicked.invoke(p) }

                upvoteButton.setOnClickListener { p.upvoteClicked.invoke(upvoteButton, p) }
                downvoteButton.setOnClickListener { p.downvoteClicked.invoke(downvoteButton, p) }

                commentButton.setOnClickListener { p.commentClicked.invoke(p) }
                shareButton.setOnClickListener { p.shareClicked.invoke(p) }

                val upvotetypedValue = TypedValue()
                val downvotetypedValue = TypedValue()
                val neutralvotetypedValue = TypedValue()
                val theme = context.theme
                theme.resolveAttribute(R.attr.colorUpvote, upvotetypedValue, true)
                theme.resolveAttribute(R.attr.colorDownvote, downvotetypedValue, true)
                theme.resolveAttribute(R.attr.colorTextAccent, neutralvotetypedValue, true)
                @ColorInt val upvotecolor = upvotetypedValue.data
                @ColorInt val downvotecolor = downvotetypedValue.data
                @ColorInt val neutralcolor = neutralvotetypedValue.data

                when(p.content.voted) {
                    VoteStatus.UPVOTE -> {
                        upvoteButton.iconTint = context.getColorStateList(upvotecolor)
                        downvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        score.setTextColor(upvotecolor)
                    }
                    VoteStatus.DOWNVOTE -> {
                        upvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        downvoteButton.iconTint = context.getColorStateList(downvotecolor)
                        score.setTextColor(downvotecolor)
                    }
                    VoteStatus.NONE -> {
                        upvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        downvoteButton.iconTint = context.getColorStateList(neutralcolor)
                        score.setTextColor(neutralcolor)
                    }
                    else -> {}
                }
            }
        }
    }
}