package com.therealorange.notreddit.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.therealorange.notreddit.adapters.CollectionAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.therealorange.notreddit.R
import kotlinx.android.synthetic.main.tab_layout.*

class FragmentCollection : Fragment() {
    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        collectionAdapter = CollectionAdapter(this@FragmentCollection)
        println("BLEGGGGHHHH")
        return inflater.inflate(R.layout.tab_layout, container, false)
    }

    fun say(s: String) = println(s)

    fun addNewFragment(f: FragmentObject) {
        println("OHNOOOOOO")
        collectionAdapter.addFragment(f)
        collectionAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("BLUBBBB")
        viewPager = pager
        viewPager.adapter = collectionAdapter

        val tabLayout = tab_layout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = collectionAdapter.getTitle(position)
        }.attach()

    }
}