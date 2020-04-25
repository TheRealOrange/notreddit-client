package com.therealorange.notreddit.tabs

import android.os.Bundle
import android.view.View
import com.therealorange.notreddit.R
import kotlinx.android.synthetic.main.fragment_popular.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentPopular : FragmentObject(R.layout.fragment_popular,"Rating") {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("ARG_OBJECT") }?.apply {
            rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                ratingText.text = rating.toString()
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                System.out.println(" C DATE is  "+currentDate+ " rating "+rating)
            }
        }
    }
}