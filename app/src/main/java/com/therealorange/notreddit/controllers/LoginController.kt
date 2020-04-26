package com.therealorange.notreddit.controllers

import android.content.Intent
import androidx.fragment.app.Fragment
import com.therealorange.notreddit.fragments.LoginFragmentDirections
import com.therealorange.notreddit.util.BottomNavigation
import kotlinx.android.synthetic.main.fragment_login.*

object LoginController : FragmentController {
    private lateinit var mcontext: Fragment

    override fun onCreateView(context: Fragment) {
        BottomNavigation.hide(true)
    }

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        with(mcontext) {
            logInSelectButton.setOnClickListener { BottomNavigation.navController.navigate(LoginFragmentDirections.loginSelectToLogInUserAction()) }
            signUpSelectButton.setOnClickListener { BottomNavigation.navController.navigate(LoginFragmentDirections.loginSelectToSignUpUserAction()) }
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