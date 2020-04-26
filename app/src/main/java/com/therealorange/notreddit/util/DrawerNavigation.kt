package com.therealorange.notreddit.util

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationView
import com.therealorange.notreddit.R

object DrawerNavigation {
    private lateinit var navMap: Map<Int, ()->Unit>
    private lateinit var navController: NavController
    private lateinit var drawer: DrawerLayout
    private lateinit var navView: NavigationView

    fun init(
        navMap: Map<Int, ()->Unit>,
        navController: NavController,
        nav_view: NavigationView,
        drawer: DrawerLayout
    ) {
        DrawerNavigation.navMap = navMap
        DrawerNavigation.navController = navController
        DrawerNavigation.drawer = drawer
        DrawerNavigation.navView = nav_view
        nav_view.setNavigationItemSelectedListener {
            val action = navMap[it.itemId] ?: return@setNavigationItemSelectedListener false
            action.invoke()
            true
        }
    }

    fun navigate(id: Int) {
        navController.navigate(id)
        drawer.closeDrawer(GravityCompat.START)
    }

    fun logout() {
        navView.menu.clear()
        navView.inflateMenu(R.menu.normal_menu)
        updateMenu(logged_out_map)
    }

    fun login() {
        navView.menu.clear()
        navView.inflateMenu(R.menu.user_menu)
        updateMenu(logged_in_map)
    }

    private fun updateMenu(navMap: Map<Int, ()->Unit>) {
        DrawerNavigation.navMap = navMap
    }

    val logged_out_map = mapOf(
        R.id.nav_loginFragment to { navigate(R.id.fragmentLogin) })

    val logged_in_map = mapOf(
        R.id.nav_profileFragment to { navigate(R.id.fragmentProfile) },
        R.id.nav_createCommunityFragment to { navigate(R.id.fragmentCreateCommunity) },
        R.id.nav_logoutFragment to { logout() })
}