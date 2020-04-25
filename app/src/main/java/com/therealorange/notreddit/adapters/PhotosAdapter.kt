package com.therealorange.notreddit.adapters

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import com.therealorange.notreddit.R
import kotlinx.android.synthetic.main.img_view.*
import kotlinx.android.synthetic.main.img_view.view.*


class PhotosAdapter(mcontext: Context, img: MutableList<Bitmap>) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    val imgs = img
    val context = mcontext

    override fun getItemCount() = imgs.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.img_view, parent, false))
    }

    fun addPhoto(bm: Bitmap): Int {
        imgs.add(bm)
        notifyDataSetChanged()
        return itemCount-1
    }

    fun changePhoto(bm: Bitmap, pos: Int) {
        imgs[pos] = bm
        notifyItemChanged(pos)
    }

    fun removePhoto(pos: Int){
        imgs.removeAt(pos)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgViewer.setImageBitmap(imgs[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val imgViewer: PhotoView = view.photoView
    }
}