package com.therealorange.notreddit.controllers

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.therealorange.notreddit.client.WebSocket
import com.therealorange.notreddit.fragments.LoginUserFragmentDirections
import com.therealorange.notreddit.util.BottomNavigation
import kotlinx.android.synthetic.main.fragment_login_user.*

object LogInUserController : FragmentController {
    private lateinit var mcontext: Fragment

    override fun onCreateView(context: Fragment) {
        BottomNavigation.hide(true)
    }

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        BottomNavigation.hide(true)
        println("ohno")
        with(mcontext) {
            logInConfirmButton.setOnClickListener {
                WebSocket.sendAuth(logInUsernameField.text.toString(), logInPasswordField.text.toString(), success = {
                    LoginUserFragmentDirections.loggedInToHomeFragment()
                }, failure = {
                    Toast.makeText(
                        mcontext.requireContext(),
                        "login error",
                        Toast.LENGTH_LONG
                    ).show()
                })
            }
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