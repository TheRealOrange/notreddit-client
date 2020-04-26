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
import com.therealorange.notreddit.client.User
import com.therealorange.notreddit.client.WebSocket
import com.therealorange.notreddit.client.data.objects.SubNotRedditInfo
import com.therealorange.notreddit.fragments.CreateCommunityFragmentDirections
import com.therealorange.notreddit.util.BottomNavigation
import com.therealorange.notreddit.util.ImageUtils.encodeToBase64
import com.therealorange.notreddit.util.ImageUtils.readBitmap
import kotlinx.android.synthetic.main.fragment_create_community.*
import java.io.File

object CreateCommunityController : FragmentController {
    private lateinit var mcontext: Fragment

    private lateinit var communityIcon: Bitmap

    private lateinit var communityBanner: Bitmap

    override fun onCreateView(context: Fragment) {
        BottomNavigation.hide(true)
    }

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        BottomNavigation.hide(true)
        communityIcon = (ResourcesCompat.getDrawable(mcontext.resources, R.drawable.ic_launcher_foreground, null) as VectorDrawable).toBitmap()
        communityBanner = (ResourcesCompat.getDrawable(mcontext.resources, R.drawable.ic_launcher_background, null) as VectorDrawable).toBitmap()
        with(mcontext) {
            createNewCommunitySelectIconButton.setOnClickListener {
                ImagePicker.with(mcontext)
                    .cropSquare()
                    .compress(4096)
                    .maxResultSize(500, 500)
                    .start(303)
            }
            createNewCommunitySelectBannerButton.setOnClickListener {
                ImagePicker.with(mcontext)
                    .crop(2f, 1f)
                    .compress(4096)
                    .maxResultSize(1500, 500)
                    .start(404)
            }
            createNewCommunityConfirmButton.setOnClickListener {
                if (valid()) {
                    WebSocket.sendAddSubNotReddit(SubNotRedditInfo(
                        -1,
                        createNewCommunityNameField.text.toString(),
                        createNewCommunityDescField.text.toString(),
                        createNewCommunityAboutField.text.toString(),
                        encodeToBase64(communityIcon)!!,
                        encodeToBase64(communityBanner)!!,
                        -1,
                        User.userinfo.userId
                    ), success =  {
                        Toast.makeText(
                            requireContext(),
                            "community created",
                            Toast.LENGTH_SHORT
                        ).show()
                        BottomNavigation.navController.navigate(CreateCommunityFragmentDirections.createCommunityCompleteToHomeAction())
                    }, failure = {
                        Toast.makeText(
                            requireContext(),
                            "community could not be created",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                }
            }
        }
    }

    fun valid(): Boolean {
        with(mcontext) {
            val name = createNewCommunityNameField.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "community name cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
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
                if (requestCode == 303) {
                    createNewCommunityIconPreview.setImageURI(fileUri)
                    if (file != null) {
                        communityIcon = file.readBitmap()
                    }
                } else if (requestCode == 404) {
                    createNewCommunityBannerPreview.setImageURI(fileUri)
                    if (file != null) {
                        communityBanner = file.readBitmap()
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