package com.therealorange.notreddit.tabs

import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.therealorange.notreddit.MainNavDirections
import com.therealorange.notreddit.R
import com.therealorange.notreddit.adapters.PostsAdapter
import com.therealorange.notreddit.client.WebSocket
import com.therealorange.notreddit.client.data.SortType
import com.therealorange.notreddit.client.data.VoteStatus
import com.therealorange.notreddit.client.data.objects.PostContent
import com.therealorange.notreddit.client.data.objects.SubNotRedditInfo
import com.therealorange.notreddit.client.data.objects.UserInfo
import com.therealorange.notreddit.util.PostItem
import kotlinx.android.synthetic.main.posts_page.*

class FragmentPosts : FragmentObject(R.layout.posts_page,"Posts", EXTRA_ID) {
    lateinit var adapter: PostsAdapter
    var posts = mutableListOf<PostItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(extraId) }?.apply {
            recyclerview(this@FragmentPosts)
            if (this.getString(extraId)?.toIntOrNull() != null) {
                WebSocket.sendGetSubNotReddit(this.getString(extraId)!!.toIntOrNull()!!) { subnotreddit->
                    WebSocket.sendGetSubNotRedditPosts(subnotreddit.subNotRedditId, SortType.BEST) { subredditposts->
                        subredditposts.forEach { postId->
                            WebSocket.sendGetPost(postId) { post->
                                var newPostItem: PostItem? = null
                                when(post) {
                                    is PostContent.TextPost -> newPostItem = PostItem.TextPostItem(
                                        post,
                                        subNotRedditClicked = { enterSubNotReddit((it as PostItem.TextPostItem).content.subnotredddit) },
                                        userClicked = { enterUserProfile((it as PostItem.TextPostItem).content.user) },
                                        titleClicked = { viewPost((it as PostItem.TextPostItem).content) },
                                        textClicked = { viewPost((it as PostItem.TextPostItem).content) },
                                        upvoteClicked = { button: Button, postItem: PostItem -> upvote(button, (postItem as PostItem.TextPostItem).content) },
                                        downvoteClicked = { button: Button, postItem: PostItem -> downvote(button, (postItem as PostItem.TextPostItem).content) },
                                        commentClicked = { viewPost((it as PostItem.TextPostItem).content) },
                                        shareClicked = { shareMenu((it as PostItem.TextPostItem).content) }
                                    )
                                    is PostContent.ImgPost -> newPostItem = PostItem.ImgPostItem(
                                        post,
                                        subNotRedditClicked = { enterSubNotReddit((it as PostItem.ImgPostItem).content.subnotredddit) },
                                        userClicked = { enterUserProfile((it as PostItem.ImgPostItem).content.user) },
                                        titleClicked = { viewPost((it as PostItem.ImgPostItem).content) },
                                        imageClicked = { imagePreview((it as PostItem.ImgPostItem).content.imgs) },
                                        upvoteClicked = { button: Button, postItem: PostItem -> upvote(button, (postId as PostItem.ImgPostItem).content) },
                                        downvoteClicked = { button: Button, postItem: PostItem -> downvote(button, (postItem as PostItem.ImgPostItem).content) },
                                        commentClicked = { viewPost((it as PostItem.ImgPostItem).content) },
                                        shareClicked = { shareMenu((it as PostItem.ImgPostItem).content) }
                                    )
                                    is PostContent.ImgTextPost -> newPostItem = PostItem.ImgTextPostItem(
                                        post,
                                        subNotRedditClicked = { enterSubNotReddit((it as PostItem.ImgTextPostItem).content.subnotredddit) },
                                        userClicked = { enterUserProfile((it as PostItem.ImgTextPostItem).content.user) },
                                        titleClicked = { viewPost((it as PostItem.ImgTextPostItem).content) },
                                        textClicked = { viewPost((it as PostItem.ImgTextPostItem).content) },
                                        imageClicked = { imagePreview((it as PostItem.ImgTextPostItem).content.imgs) },
                                        upvoteClicked = { button: Button, postItem: PostItem -> upvote(button, (postItem as PostItem.ImgTextPostItem).content) },
                                        downvoteClicked = { button: Button, postItem: PostItem -> downvote(button, (postItem as PostItem.ImgTextPostItem).content) },
                                        commentClicked = { viewPost((it as PostItem.ImgTextPostItem).content) },
                                        shareClicked = { shareMenu((it as PostItem.ImgTextPostItem).content) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun enterSubNotReddit(subNotRedditInfo: SubNotRedditInfo) {
        MainNavDirections.visitSubNotRedditAction(subNotRedditInfo.subNotRedditId)
    }

    fun enterUserProfile(userInfo: UserInfo) {

    }

    fun viewPost(postItem: PostContent) {

    }

    fun imagePreview(imgs: List<String>) {

    }

    fun upvote(btn: Button, p: PostContent) {
        when (p) {
            is PostContent.TextPost -> updateVote(newVoteStatus(VoteStatus.UPVOTE, p.voted), p.postId)
            is PostContent.ImgPost -> updateVote(newVoteStatus(VoteStatus.UPVOTE, p.voted), p.postId)
            is PostContent.ImgTextPost -> updateVote(newVoteStatus(VoteStatus.UPVOTE, p.voted), p.postId)
        }
    }

    fun downvote(btn: Button, p: PostContent) {
        when (p) {
            is PostContent.TextPost -> updateVote(newVoteStatus(VoteStatus.DOWNVOTE, p.voted), p.postId)
            is PostContent.ImgPost -> updateVote(newVoteStatus(VoteStatus.DOWNVOTE, p.voted), p.postId)
            is PostContent.ImgTextPost -> updateVote(newVoteStatus(VoteStatus.DOWNVOTE, p.voted), p.postId)
        }
    }

    fun newVoteStatus(v: VoteStatus, old: VoteStatus): VoteStatus {
        if (v == old) return VoteStatus.NONE
        else return v
    }

    fun updateVote(voteStatus: VoteStatus, postId: Int) {
        val pos = posts.indexOfFirst { when(it) {
                is PostItem.TextPostItem -> it.content.postId == postId
                is PostItem.ImgPostItem -> it.content.postId == postId
                is PostItem.ImgTextPostItem -> it.content.postId == postId
            }
        }
        if (pos >= 0) WebSocket.sendPostVote(pos, voteStatus) {
            if (it) {
                WebSocket.sendGetPost(postId) { postcontent->
                    var editedPostItem: PostItem? = null
                    when (posts[pos]) {
                        is PostItem.TextPostItem -> {
                            val temp = posts[pos] as PostItem.TextPostItem
                            editedPostItem = PostItem.TextPostItem(
                                postcontent as PostContent.TextPost,
                                subNotRedditClicked = temp.subNotRedditClicked,
                                userClicked = temp.userClicked,
                                menuClicked = temp.menuClicked,
                                titleClicked = temp.titleClicked,
                                textClicked = temp.textClicked,
                                upvoteClicked = temp.upvoteClicked,
                                downvoteClicked = temp.downvoteClicked,
                                commentClicked = temp.commentClicked,
                                shareClicked = temp.shareClicked
                            )
                        }
                        is PostItem.ImgPostItem -> {
                            val temp = posts[pos] as PostItem.ImgPostItem
                            editedPostItem = PostItem.ImgPostItem(
                                postcontent as PostContent.ImgPost,
                                subNotRedditClicked = temp.subNotRedditClicked,
                                userClicked = temp.userClicked,
                                menuClicked = temp.menuClicked,
                                titleClicked = temp.titleClicked,
                                imageClicked = temp.imageClicked,
                                upvoteClicked = temp.upvoteClicked,
                                downvoteClicked = temp.downvoteClicked,
                                commentClicked = temp.commentClicked,
                                shareClicked = temp.shareClicked
                            )
                        }
                        is PostItem.ImgTextPostItem -> {
                            val temp = posts[pos] as PostItem.ImgTextPostItem
                            editedPostItem = PostItem.ImgTextPostItem(
                                postcontent as PostContent.ImgTextPost,
                                subNotRedditClicked = temp.subNotRedditClicked,
                                userClicked = temp.userClicked,
                                menuClicked = temp.menuClicked,
                                titleClicked = temp.titleClicked,
                                textClicked = temp.textClicked,
                                imageClicked = temp.imageClicked,
                                upvoteClicked = temp.upvoteClicked,
                                downvoteClicked = temp.downvoteClicked,
                                commentClicked = temp.commentClicked,
                                shareClicked = temp.shareClicked
                            )
                        }
                    }
                    posts[pos] = editedPostItem
                    adapter.updateItem(editedPostItem, pos)
                }
            }
        }
    }

    fun shareMenu(postItem: PostContent) {

    }

    fun recyclerview(context: Fragment) {
        with (context) {
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = PostsAdapter(context.requireContext(), mutableListOf())
            subnotredditpageRecycler.adapter = adapter
            subnotredditpageRecycler.layoutManager = layoutManager
        }
    }
}
