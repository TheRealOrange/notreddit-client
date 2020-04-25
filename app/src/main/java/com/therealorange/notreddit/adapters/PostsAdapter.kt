package com.therealorange.notreddit.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.therealorange.notreddit.R
import com.therealorange.notreddit.client.data.objects.PostContent


class PostsAdapter(private var items: List<PostContent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_TEXT = 1
        private const val TYPE_IMG = 2
        private const val TYPE_IMGTEXT = 3
        private const val TYPE_LINK = 4
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TEXT->TextPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_text_post, parent, false))
            TYPE_IMG->ImgPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_img_post, parent, false))
            TYPE_IMGTEXT->ImgTextPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_imgtext_post, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position])
    }

    class TextPostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(p: PostContent.TextPost) {
            with (view) {

            }
        }
    }


    class ImgPostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(p: PostContent.TextPost) {
            with (view) {

            }
        }
    }

    class ImgTextPostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(p: PostContent.TextPost) {
            with (view) {

            }
        }
    }
}