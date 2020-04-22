package com.therealorange.notreddit.controllers

import android.content.Intent
import androidx.fragment.app.Fragment
import com.therealorange.notreddit.tabs.FragmentHome
import com.therealorange.notreddit.tabs.FragmentNews
import com.therealorange.notreddit.tabs.FragmentPopular
import kotlinx.android.synthetic.main.fragment_main.*

object MainController : FragmentController {
    private lateinit var mcontext: Fragment

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        with(mcontext) {
            val newsFragment = FragmentNews()
            val homeFragment = FragmentHome()
            val popularFragment = FragmentPopular()
            println("OKKKKKKK")
            println(mainTabView)
            //mainTabView.say("ok")
            println("NOTOKKKKKK")
            //mainTabView.addNewFragment(newsFragment)
            //mainTabView.addNewFragment(homeFragment)
            //mainTabView.addNewFragment(popularFragment)
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