package com.therealorange.notreddit.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.therealorange.notreddit.tabs.FragmentObject

class CollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val fragments: MutableList<FragmentObject> = mutableListOf()
    val fragmentTitles: MutableList<String> = mutableListOf()

    override fun getItemCount() = fragments.size

    fun addFragment(f : FragmentObject) {
        fragments.add(f)
        fragmentTitles.add(f.tabtitle)
    }

    fun getTitle(position: Int) = fragmentTitles[position]

    override fun createFragment(position: Int): Fragment {
        // Return a NEW tab_layout instance in createFragment(int)
        val fragment = fragments[position]
        fragment.arguments = Bundle().apply {
            // Our object is just an integer :-P
            putInt("ARG_OBJECT", position + 1)
        }
        return fragment
    }
}