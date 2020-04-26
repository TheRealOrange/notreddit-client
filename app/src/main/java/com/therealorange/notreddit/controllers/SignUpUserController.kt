package com.therealorange.notreddit.controllers

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.VectorDrawable
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.therealorange.notreddit.R
import com.therealorange.notreddit.client.WebSocket
import com.therealorange.notreddit.client.data.objects.UserInfo
import com.therealorange.notreddit.fragments.SignUpUserFragmentDirections
import com.therealorange.notreddit.util.BottomNavigation
import com.therealorange.notreddit.util.ImageUtils.readBitmap
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_signup_user.*
import java.io.File

object SignUpUserController : FragmentController {
    private lateinit var mcontext: Fragment

    private lateinit var userIcon: Bitmap

    private lateinit var userBanner: Bitmap

    override fun onCreateView(context: Fragment) {
        BottomNavigation.hide(true)
    }

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        BottomNavigation.hide(true)
        userIcon = (ResourcesCompat.getDrawable(mcontext.resources, R.drawable.ic_launcher_foreground, null) as VectorDrawable).toBitmap()
        userBanner = (ResourcesCompat.getDrawable(mcontext.resources, R.drawable.ic_launcher_background, null) as VectorDrawable).toBitmap()
        with(mcontext) {
            signUpSelectIconButton.setOnClickListener {
                ImagePicker.with(mcontext)
                    .cropSquare()
                    .compress(4096)
                    .maxResultSize(500, 500)
                    .start(101)
            }
            signUpSelectBannerButton.setOnClickListener {
                ImagePicker.with(mcontext)
                    .crop(2f, 1f)
                    .compress(4096)
                    .maxResultSize(1500, 500)
                    .start(202)
            }
            signUpConfirmButton.setOnClickListener {
                if (valid()) {
                    val uname = signUpUsernameField.text.toString()
                    val pwd1 = signUpPasswordField1.text.toString()
                    WebSocket.sendAddUser(uname, pwd1, userIcon, userBanner,
                        success = {
                            BottomNavigation.navController.navigate(SignUpUserFragmentDirections.signUpCompleteToLogInAction())
                        }, failure = {
                            Toast.makeText(
                                requireContext(),
                                "error failed to add user",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                }
            }
        }
    }

    fun valid(): Boolean {
        with(mcontext) {
            val uname = signUpUsernameField.text.toString()
            val pwd1 = signUpPasswordField1.text.toString()
            val pwd2 = signUpPasswordField2.text.toString()

            if (uname.isEmpty() || pwd1.isEmpty() || pwd1.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "username/password cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            if (pwd1 != pwd2) {
                Toast.makeText(requireContext(), "passwords do not match", Toast.LENGTH_SHORT)
                    .show()
                return false
            }
        }
        return true
    }

    override fun restoreState() {
    }

    override fun onDestroyView() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        with(mcontext) {
            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data
                val file: File? = ImagePicker.getFile(data)
                if (requestCode == 101) {
                    signUpIconPreview.setImageURI(fileUri)
                    if (file != null) {
                        userIcon = file.readBitmap()
                    }
                } else if (requestCode == 202) {
                    signUpBannerPreview.setImageURI(fileUri)
                    if (file != null) {
                        userBanner = file.readBitmap()
                    }
                }
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}