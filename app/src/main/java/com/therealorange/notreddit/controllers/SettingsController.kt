package com.therealorange.notreddit.controllers

import android.content.Intent
import androidx.fragment.app.Fragment
import com.therealorange.notreddit.util.BottomNavigation

object SettingsController : FragmentController {
    private lateinit var mcontext: Fragment

    override fun onCreateView(context: Fragment) {
        BottomNavigation.hide(true)
    }

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        with(mcontext) {
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