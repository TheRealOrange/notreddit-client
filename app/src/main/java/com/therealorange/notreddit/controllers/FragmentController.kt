package com.therealorange.notreddit.controllers

import android.content.Intent
import androidx.fragment.app.Fragment

interface FragmentController {
    fun onViewCreated(context: Fragment)
    fun restoreState()
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun onDestroyView()
}
