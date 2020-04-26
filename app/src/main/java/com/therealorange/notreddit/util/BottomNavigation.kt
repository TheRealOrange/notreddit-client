package com.therealorange.notreddit.util

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.therealorange.notreddit.MainActivity
import com.therealorange.notreddit.MainNavDirections
import com.therealorange.notreddit.R
import com.therealorange.notreddit.dialogs.BottomSheetSelectPostTypeFragment

object BottomNavigation {
    private lateinit var navMap: Map<Int, (() -> Unit)>
    lateinit var navController: NavController
    private lateinit var bottomBar: BottomNavigationView

    const val POST_TEXT = 1
    const val POST_IMG = 2
    const val POST_TEXTIMG = 3

    fun init(
        navMap: Map<Int, (() -> Unit)>,
        navController: NavController,
        nav_view: BottomNavigationView
    ) {
        BottomNavigation.navMap = navMap
        BottomNavigation.navController = navController
        BottomNavigation.bottomBar = nav_view
        nav_view.setOnNavigationItemSelectedListener {
            val destination = navMap[it.itemId] ?: return@setOnNavigationItemSelectedListener false
            destination.invoke()
            true
        }
    }

    fun navigate(id: Int) {
        bottomBar.visibility = View.VISIBLE
        navController.navigate(id)
    }

    fun hide(bool: Boolean) {
        println("kjasbvkjfsd"+bool.toString())
        if (bool) {
            bottomBar.visibility = View.GONE
            MainActivity.appBar.visibility = View.GONE
        }
        else {
            bottomBar.visibility = View.VISIBLE
            MainActivity.appBar.visibility = View.VISIBLE
        }
    }

    //val normalMap = mapOf(R.id.nav_loginFragment  to R.id.fragmentLogin)
    val userMap = mapOf(
        R.id.nav_mainFragment to { navigate(R.id.fragmentMain) },
        R.id.nav_searchFragment to { navigate(R.id.fragmentSearch) },
        R.id.nav_newPostFragment to {
            val dialog = BottomSheetSelectPostTypeFragment.newInstance({
                navController.navigate(MainNavDirections.createNewPostAction(POST_TEXT))
            }, {
                navController.navigate(MainNavDirections.createNewPostAction(POST_IMG))
            }, {
                navController.navigate(MainNavDirections.createNewPostAction(POST_TEXTIMG))
            })
            dialog!!.show(MainActivity.appFragmentManager, "tag")
        },
        R.id.nav_inboxFragment to { navigate(R.id.fragmentInbox) })
}
