package com.therealorange.notreddit.tabs

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.therealorange.notreddit.R
import com.therealorange.notreddit.adapters.PhotosAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class FragmentHome : FragmentObject(R.layout.fragment_home,"Home") {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("ARG_OBJECT") }?.apply {
        }
    }
}