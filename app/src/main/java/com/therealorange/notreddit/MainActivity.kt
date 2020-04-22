package com.therealorange.notreddit

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.therealorange.notreddit.client.Client
import com.therealorange.notreddit.client.WebSocket
import com.therealorange.notreddit.util.BottomNavigation
import com.therealorange.notreddit.util.DrawerNavigation
import com.therealorange.notreddit.util.Preferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    var mbottomNavigation: BottomNavigationView? = null
    private var serverConnect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent.getBooleanExtra("LOGOUT", false))
        {
            finish();
        }

        Client.connectCallbacks.add {
            serverConnect = true
        }
        //Client.init()
        //while(!serverConnect) {}
        WebSocket
        Preferences.init(this)
        setTheme(
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) R.style.AppTheme
            else R.style.AppTheme_Light
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //mbottomNavigation = findViewById(R.id.bottom_navigation);
        var mToolbar = findViewById<Toolbar>(R.id.toolbar)

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
        DrawerNavigation.init(DrawerNavigation.logged_in_map, navController, nav_view, drawer_layout)

        settingsButton.setOnClickListener {
            DrawerNavigation.navigate(R.id.fragmentSettings)
            println("bigstupid")
        }

        darkModeToggle.setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

            recreate()
        }
    }
}
