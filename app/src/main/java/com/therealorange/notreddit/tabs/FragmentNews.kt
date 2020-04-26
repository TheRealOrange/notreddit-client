package com.therealorange.notreddit.tabs

import android.os.Bundle
import android.view.View
import com.therealorange.notreddit.R
import java.util.*

class FragmentNews : FragmentObject(R.layout.fragment_news,"News") {
    var stop = false
    val rnd = Random()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("ARG_OBJECT") }?.apply {
        }
    }
}
