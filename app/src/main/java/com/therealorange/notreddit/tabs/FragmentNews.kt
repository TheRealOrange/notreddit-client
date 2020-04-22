package com.therealorange.notreddit.tabs

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.therealorange.notreddit.R
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class FragmentNews : FragmentObject(R.layout.fragment_news,"Epilepsy") {
    var stop = false
    val rnd = Random()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("ARG_OBJECT") }?.apply {
        }
    }
}
