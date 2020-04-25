package com.therealorange.notreddit.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.therealorange.notreddit.R
import com.therealorange.notreddit.client.data.objects.PostContent
import com.therealorange.notreddit.data.objects.SubNotReddit
import com.therealorange.notreddit.client.data.objects.UserInfo
import com.therealorange.notreddit.util.DownloadImageTask
import kotlinx.android.synthetic.main.headder_no_subnotreddit.*
import kotlinx.android.synthetic.main.header_subnotreddit.*
import kotlinx.android.synthetic.main.img_content.*
import kotlinx.android.synthetic.main.post_footer.*
import kotlinx.android.synthetic.main.text_content.*

class Post(c: PostContent, header: Boolean, vote: ((p:Post, upvote: Boolean)->Unit), menu: ((p: Post)->Unit), comments: ((p: Post)->Unit), share: ((p: Post)->Post)) : Fragment() {
    var postId: Int = -1
    var title: String = ""
    var score: Int = 0
    var commentNum: Int = 0
    lateinit var user: UserInfo
    lateinit var subnotreddit: SubNotReddit
    lateinit var time: String
    val content = c
    val header = header

    val voteFunc = vote
    val menuFunc = menu
    val commentsFunc = comments
    val shareFunc= share

    init {
        when(content) {
            is PostContent.ImgPost ->{
                postId = content.postId
                title = content.title
                score = content.score
                commentNum = content.comments
                user = content.user
                subnotreddit = content.subnotredddit
                time = content.time
            }
            is PostContent.ImgTextPost ->{
                postId = content.postId
                title = content.title
                score = content.score
                commentNum = content.comments
                user = content.user
                subnotreddit = content.subnotredddit
                time = content.time
            }
            is PostContent.TextPost ->{
                postId = content.postId
                title = content.title
                score = content.score
                commentNum = content.comments
                user = content.user
                subnotreddit = content.subnotredddit
                time = content.time
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(when(content) {
            is PostContent.ImgPost ->if(header) R.layout.card_img_post else R.layout.card_noheader_img_post
            is PostContent.ImgTextPost ->if(header) R.layout.card_imgtext_post else R.layout.card_noheader_imgtext_post
            is PostContent.TextPost ->if(header) R.layout.card_text_post else R.layout.card_noheader_text_post
            else ->R.layout.card_text_post
        }, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (header) {
            subNotRedditName.text = subnotreddit.name
            DownloadImageTask(subNotRedditIcon).execute(subnotreddit.icon)
            n_postUserName.text = user.username
            n_postTime.text = time
            n_postLink.text = "blegh"

            n_menuButton.setOnClickListener { menuFunc.invoke(this) }
        } else  {
            postUserName.text = user.username
            postTime.text = time
            postLink.text = "blegh"

            menuButton.setOnClickListener { menuFunc.invoke(this) }
        }

        when(content) {
            is PostContent.ImgPost ->{
                DownloadImageTask(postImg).execute(content.imgs[0])
            }
            is PostContent.ImgTextPost ->{
                DownloadImageTask(postImg).execute(content.imgs[0])
                postText.text = content.text
            }
            is PostContent.TextPost ->{
                postText.text = content.text
            }
        }

        upvoteButton.setOnClickListener { voteFunc.invoke(this,true) }
        downvoteButton.setOnClickListener { voteFunc.invoke(this, false) }

        commentButton.setOnClickListener { commentsFunc.invoke(this) }
        shareButton.setOnClickListener { shareFunc.invoke(this) }

    }
}