package com.therealorange.notreddit.controllers

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.therealorange.notreddit.adapters.CollectionAdapter
import com.therealorange.notreddit.tabs.FragmentHome
import com.therealorange.notreddit.tabs.FragmentNews
import com.therealorange.notreddit.tabs.FragmentPopular
import com.therealorange.notreddit.util.BottomNavigation
import kotlinx.android.synthetic.main.fragment_main.*

object MainController : FragmentController {
    private lateinit var mcontext: Fragment
    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(context: Fragment) {
        collectionAdapter = CollectionAdapter(context)

        val newsFragment = FragmentNews()
        val homeFragment = FragmentHome()
        val popularFragment = FragmentPopular()
        collectionAdapter.addFragment(newsFragment)
        collectionAdapter.addFragment(homeFragment)
        collectionAdapter.addFragment(popularFragment)
        BottomNavigation.hide(false)
    }


    override fun onViewCreated(context: Fragment) {
        mcontext = context
        with(mcontext) {
            viewPager = pager
            viewPager.adapter = collectionAdapter

            val tabLayout = tab_layout
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = collectionAdapter.getTitle(position)
            }.attach()
        }
    }

    override fun restoreState() {
    }

    override fun onDestroyView() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}