package com.therealorange.notreddit.util

import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.therealorange.notreddit.R

object BottomNavigation {
    private lateinit var navMap: Map<Int, Int>

    fun init(
        navMap: Map<Int, Int>,
        navController: NavController,
        nav_view: BottomNavigationView
    ) {
        BottomNavigation.navMap = navMap
        nav_view.setOnNavigationItemSelectedListener {
            val destination = navMap[it.itemId] ?: return@setOnNavigationItemSelectedListener false
            navController.navigate(destination)
            true
        }
    }

    //val normalMap = mapOf(R.id.nav_loginFragment  to R.id.fragmentLogin)
    val userMap = mapOf(
        R.id.nav_mainFragment to R.id.fragmentMain,
        R.id.nav_searchFragment to R.id.fragmentSearch,
        R.id.nav_newPostFragment to R.id.fragmentNewPost,
        R.id.nav_inboxFragment to R.id.fragmentInbox)
}
