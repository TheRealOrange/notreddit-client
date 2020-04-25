package com.therealorange.notreddit.controllers

import android.R
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.therealorange.notreddit.MainActivity
import com.therealorange.notreddit.adapters.PhotoPreviewAdapter
import com.therealorange.notreddit.client.User
import com.therealorange.notreddit.client.WebSocket
import com.therealorange.notreddit.client.data.VoteStatus
import com.therealorange.notreddit.client.data.objects.PostContent
import com.therealorange.notreddit.fragments.NewPostFragmentArgs
import com.therealorange.notreddit.util.BottomNavigation.POST_IMG
import com.therealorange.notreddit.util.BottomNavigation.POST_TEXT
import com.therealorange.notreddit.util.BottomNavigation.POST_TEXTIMG
import com.therealorange.notreddit.util.ImageUtils
import kotlinx.android.synthetic.main.fragment_new_post.*
import kotlin.math.roundToInt
import kotlin.math.sqrt


object NewPostController : FragmentController {
    private lateinit var mcontext: Fragment
    private var subNotRedditId = -1
    private var selectedImgs = mutableListOf<Bitmap>()

    override fun onCreateView(context: Fragment) {
    }

    override fun onViewCreated(context: Fragment) {
        mcontext = context
        with(mcontext) {
            val args: NewPostFragmentArgs by navArgs()
            when (args.postType) {
                POST_TEXT->{
                    newPostAddImg.visibility = View.GONE
                    newPostPhotoPreview.visibility = View.GONE
                }
                POST_IMG->{
                    newPostTextEdit.visibility = View.GONE
                    recyclerview(this)
                }
                POST_TEXTIMG->{
                    recyclerview(this)
                }
            }
        }
    }

    fun recyclerview(context: Fragment) {
        with (context) {
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            newPostPhotoPreview.adapter = PhotoPreviewAdapter(context.requireContext(), mutableListOf())
            newPostPhotoPreview.layoutManager = layoutManager
        }
    }

    fun valid(): Boolean {
        with(mcontext) {
            if (subNotRedditId == -1 || !User.userinfo.subscribed.contains(subNotRedditId)) {
                error("Please select a community you've joined").show()
                return false
            }
            val args: NewPostFragmentArgs by navArgs()
            when (args.postType) {
                POST_TEXT -> {
                    if (newPostTitleEdit.text.isEmpty() || newPostTextEdit.text.isEmpty()) {
                        error("Post must have title and content").show()
                    }
                }
                POST_IMG -> {
                    if (newPostTitleEdit.text.isEmpty() || selectedImgs.size <= 0) {
                        error("Post must have title and at least 1 image").show()
                    }
                }
                POST_TEXTIMG -> {
                    if (newPostTitleEdit.text.isEmpty() || newPostTextEdit.text.isEmpty() || selectedImgs.size <= 0) {
                        error("Post must have title, content and at least 1 image").show()
                    }
                }
            }
        }
        return true
    }

    fun createpost(callback: ((post: PostContent) -> Unit)) {
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
                            selectedImgs.forEach { ImageUtils.encodeTobase64(ImageUtils.scaleBitmap(it))?.let { it1 ->
                                images.add(it1)
                            } }
                            post = PostContent.ImgPost(-1, newPostTitleEdit.text.toString(), -1, -1, User.userinfo, info, VoteStatus.UPVOTE, "", images.toList())
                        }
                        POST_TEXTIMG -> {
                            val images = mutableListOf<String>()
                            selectedImgs.forEach { ImageUtils.encodeTobase64(ImageUtils.scaleBitmap(it))?.let { it1 ->
                                images.add(it1)
                            } }
                            post = PostContent.ImgTextPost(-1, newPostTitleEdit.text.toString(), -1, -1, User.userinfo, info, VoteStatus.UPVOTE, "", images.toList(), newPostTextEdit.text.toString())
                        }
                    }
                }
            }
        }
    }

    fun error(s: String) = Toast.makeText(MainActivity.appContext,s, Toast.LENGTH_LONG)

    override fun restoreState() {
    }

    override fun onDestroyView() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}