package com.therealorange.notreddit.controllers

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.therealorange.notreddit.adapters.PhotoPreviewAdapter
import com.therealorange.notreddit.client.User
import com.therealorange.notreddit.client.WebSocket
import com.therealorange.notreddit.client.data.VoteStatus
import com.therealorange.notreddit.client.data.objects.PostContent
import com.therealorange.notreddit.dialogs.BottomMenuItem
import com.therealorange.notreddit.dialogs.BottomSheetMenu
import com.therealorange.notreddit.fragments.NewPostFragmentArgs
import com.therealorange.notreddit.fragments.NewPostFragmentDirections
import com.therealorange.notreddit.util.BottomNavigation
import com.therealorange.notreddit.util.BottomNavigation.POST_IMG
import com.therealorange.notreddit.util.BottomNavigation.POST_TEXT
import com.therealorange.notreddit.util.BottomNavigation.POST_TEXTIMG
import com.therealorange.notreddit.util.ImageUtils
import kotlinx.android.synthetic.main.fragment_new_post.*


object NewPostController : FragmentController {
    private lateinit var mcontext: Fragment
    private var subNotRedditId = -1
    private var selectedImgs = mutableListOf<Bitmap>()
    lateinit var menu: BottomSheetMenu
    lateinit var adapter: PhotoPreviewAdapter

    override fun onCreateView(context: Fragment) {
        BottomNavigation.hide(true)
        menu = BottomSheetMenu(mcontext.requireContext(), mutableListOf())
        User.userinfo.subscribed.forEach { subnotreddit ->
            WebSocket.sendGetSubNotReddit(subnotreddit) { info ->
                menu.addItem(BottomMenuItem(
                    info.subNotRedditId,
                    true,
                    info.icon,
                    info.name
                ) {
                    subNotRedditId = it.id
                })
            }
        }
    }

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        with(mcontext) {
            newPostDismissButton.setOnClickListener { NewPostFragmentDirections.postCreatedToHomeFragment() }
            newPostConfirmButton.setOnClickListener {
                if (valid()) {
                    createpost(success = {
                        msg("Post created successfully")
                        NewPostFragmentDirections.postCreatedToHomeFragment()
                    }, failure = {
                        msg("Failed to create post")
                    })
                }
            }
            val args: NewPostFragmentArgs by navArgs()
            when (args.postType) {
                POST_TEXT->{
                    postTypeText.text = "Text Post"
                    newPostAddImg.visibility = View.GONE
                    newPostPhotoPreview.visibility = View.GONE
                }
                POST_IMG->{
                    postTypeText.text = "Image Post"
                    newPostTextEdit.visibility = View.GONE
                    recyclerview(this)
                }
                POST_TEXTIMG->{
                    postTypeText.text = "Text & Image Post"
                    recyclerview(this)
                }
            }
        }
    }

    fun recyclerview(context: Fragment) {
        with (context) {
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = PhotoPreviewAdapter(context.requireContext(), mutableListOf())
            newPostPhotoPreview.adapter = adapter
            newPostPhotoPreview.layoutManager = layoutManager
        }
    }

    fun valid(): Boolean {
        with(mcontext) {
            if (subNotRedditId == -1 || !User.userinfo.subscribed.contains(subNotRedditId)) {
                msg("Please select a community you've joined").show()
                return false
            }
            val args: NewPostFragmentArgs by navArgs()
            when (args.postType) {
                POST_TEXT -> {
                    if (newPostTitleEdit.text.isEmpty() || newPostTextEdit.text.isEmpty()) {
                        msg("Post must have title and content").show()
                    }
                }
                POST_IMG -> {
                    if (newPostTitleEdit.text.isEmpty() || selectedImgs.size <= 0) {
                        msg("Post must have title and at least 1 image").show()
                    }
                }
                POST_TEXTIMG -> {
                    if (newPostTitleEdit.text.isEmpty() || newPostTextEdit.text.isEmpty() || selectedImgs.size <= 0) {
                        msg("Post must have title, content and at least 1 image").show()
                    }
                }
            }
        }
        return true
    }

    fun createpost(success: (() -> Unit), failure: (() -> Unit)) {
        if (valid()) {
            with(mcontext) {
                val args: NewPostFragmentArgs by navArgs()
                WebSocket.sendGetSubNotReddit(subNotRedditId) { info->
                    var post: PostContent? = null
                    when (args.postType) {
                        POST_TEXT -> {
                            post = PostContent.TextPost(-1, newPostTitleEdit.text.toString(), -1, -1, User.userinfo, info, VoteStatus.UPVOTE, "", newPostTextEdit.text.toString())
                        }
                        POST_IMG -> {
                            val images = mutableListOf<String>()
                            selectedImgs.forEach { ImageUtils.encodeToBase64(ImageUtils.scaleBitmap(it))?.let { it1 ->
                                images.add(it1)
                            } }
                            post = PostContent.ImgPost(-1, newPostTitleEdit.text.toString(), -1, -1, User.userinfo, info, VoteStatus.UPVOTE, "", images.toList())
                        }
                        POST_TEXTIMG -> {
                            val images = mutableListOf<String>()
                            selectedImgs.forEach { ImageUtils.encodeToBase64(ImageUtils.scaleBitmap(it))?.let { it1 ->
                                images.add(it1)
                            } }
                            post = PostContent.ImgTextPost(-1, newPostTitleEdit.text.toString(), -1, -1, User.userinfo, info, VoteStatus.UPVOTE, "", images.toList(), newPostTextEdit.text.toString())
                        }
                    }
                    if (post != null) {
                        WebSocket.sendAddPost(post, success, failure)
                    } else failure.invoke()
                }
            }
        }
    }

    fun msg(s: String) = Toast.makeText(mcontext.requireContext(),s, Toast.LENGTH_LONG)

    override fun restoreState() {
    }

    override fun onDestroyView() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}