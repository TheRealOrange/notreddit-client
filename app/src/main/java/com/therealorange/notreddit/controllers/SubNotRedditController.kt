package com.therealorange.notreddit.controllers

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.therealorange.notreddit.adapters.CollectionAdapter
import com.therealorange.notreddit.client.User
import com.therealorange.notreddit.client.WebSocket
import com.therealorange.notreddit.fragments.SubNotRedditFragmentArgs
import com.therealorange.notreddit.tabs.FragmentAbout
import com.therealorange.notreddit.tabs.FragmentPosts
import com.therealorange.notreddit.util.BottomNavigation
import kotlinx.android.synthetic.main.fragment_subnotreddit.*

object SubNotRedditController : FragmentController {
    private lateinit var mcontext: Fragment
    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var viewPager: ViewPager2

    private const val EXTRA_ID = "subnotreddit"

    override fun onCreateView(context: Fragment) {
        with(mcontext) {
            val args: SubNotRedditFragmentArgs by navArgs()
            collectionAdapter = CollectionAdapter(context, EXTRA_ID, args.subnotredditId.toString())

            val postsFragment = FragmentPosts()
            val aboutFragment = FragmentAbout()
            collectionAdapter.addFragment(postsFragment)
            collectionAdapter.addFragment(aboutFragment)
        }
        BottomNavigation.hide(true)
    }

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        with(mcontext) {
            val args: SubNotRedditFragmentArgs by navArgs()
            updateData()
            subNotRedditJoinButton.setOnClickListener {
                WebSocket.sendSubNotRedditSubscribe(!User.userinfo.subscribed.contains(args.subnotredditId), args.subnotredditId) {
                    updateButton()
                }
            }
        }
    }

    fun updateData() {
        with(mcontext) {
            val args: SubNotRedditFragmentArgs by navArgs()
            WebSocket.sendGetSubNotReddit(args.subnotredditId) {
                subNotRedditPageName.text = it.name
                subNotRedditPageDescription.text = it.description
                updateButton()
            }
        }
    }

    fun updateButton() {
        with(mcontext) {
            val args: SubNotRedditFragmentArgs by navArgs()
            if (User.userinfo.subscribed.contains(args.subnotredditId)) subNotRedditJoinButton.text = "UNSUBSCRIBE"
            else subNotRedditJoinButton.text = "JOIN"
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