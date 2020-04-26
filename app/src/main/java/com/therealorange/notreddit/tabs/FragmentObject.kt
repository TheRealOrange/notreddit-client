package com.therealorange.notreddit.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

open class FragmentObject(id: Int, title: String, val extraId: String = "ARG_OBJECT") : Fragment() {
    var tabid = id
    var tabtitle = title

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(tabid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(extraId) }?.apply {
            val textView: TextView = view.findViewById(android.R.id.text1)
            textView.text = tabtitle
        }
    }
}