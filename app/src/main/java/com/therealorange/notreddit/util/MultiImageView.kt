package com.therealorange.notreddit.util

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.therealorange.notreddit.R
import com.therealorange.notreddit.adapters.PhotosAdapter
import kotlinx.android.synthetic.main.multi_imageview_layout.*

class MultiImageView : Fragment() {
    private lateinit var imageAdapter: PhotosAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imageAdapter = PhotosAdapter(this@MultiImageView)
        return inflater.inflate(R.layout.multi_imageview_layout, container, false)
    }

    fun addImage(bm: Bitmap) {
        imageAdapter.addPhoto(bm)
        imageAdapter.notifyDataSetChanged()
    }
    fun removeImage(pos: Int) {
        imageAdapter.removePhoto(pos)
        imageAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager = imgpager
        viewPager.adapter = imageAdapter
    }
}