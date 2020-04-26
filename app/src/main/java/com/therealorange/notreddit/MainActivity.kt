package com.therealorange.notreddit

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.ContentFrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.therealorange.notreddit.client.Client
import com.therealorange.notreddit.client.WebSocket
import com.therealorange.notreddit.util.BottomNavigation
import com.therealorange.notreddit.util.DrawerNavigation
import com.therealorange.notreddit.util.ImageCaching
import com.therealorange.notreddit.util.Preferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {
    private lateinit var navController: NavController
    var mbottomNavigation: BottomNavigationView? = null
    private var serverConnect = false

    companion object {
        lateinit var appContext: Context
        lateinit var appFragmentManager: FragmentManager
        lateinit var appBar: AppBarLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appContext = applicationContext
        appFragmentManager = supportFragmentManager
        if (intent.getBooleanExtra("LOGOUT", false))
        {
            finish();
        }

        Client.connectCallbacks.add {
            serverConnect = true
        }
        ImageCaching.init(applicationContext)
        //Client.init()
        //while(!serverConnect) {}
        WebSocket
        Preferences.init(this)
        if (Preferences.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setTheme(
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) R.style.AppTheme
            else R.style.AppTheme_Light
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //mbottomNavigation = findViewById(R.id.bottom_navigation);
        var mToolbar = findViewById<Toolbar>(R.id.toolbar)
        appBar = app_bar_top

        //setSupportActionBar(mToolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, mToolbar, 0, 0)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        drawer_layout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        navController = nav_host_fragment.findNavController()
        BottomNavigation.init(
            BottomNavigation.userMap, navController,
            bottom_navigation as BottomNavigationView
        )
        DrawerNavigation.init(DrawerNavigation.logged_out_map, navController, nav_view, drawer_layout)

        settingsButton.setOnClickListener {
            DrawerNavigation.navigate(R.id.fragmentSettings)
            println("bigstupid")
        }

        darkModeToggle.setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Preferences.setDarkMode(false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Preferences.setDarkMode(true)
            }

            recreate()
        }
    }
}
