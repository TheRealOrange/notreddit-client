package com.therealorange.notreddit.tabs

import android.os.Bundle
import android.view.View
import com.therealorange.notreddit.R

class FragmentHome : FragmentObject(R.layout.fragment_home,"Home") {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("ARG_OBJECT") }?.apply {
        }
    }
}