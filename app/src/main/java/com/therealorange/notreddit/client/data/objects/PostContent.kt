package com.therealorange.notreddit.client.data.objects

import com.therealorange.notreddit.client.data.VoteStatus
import kotlinx.serialization.Serializable

@Serializable
open class PostContent {
    @Serializable
    data class ImgPost(val postId: Int, val title: String, val score: Int, val comments: Int, val user: UserInfo, val subnotredddit: SubNotRedditInfo, val voted: VoteStatus, val time: String, val imgs: List<String>) : PostContent()

    @Serializable
    data class ImgTextPost(val postId: Int, val title: String, val score: Int, val comments: Int, val user: UserInfo, val subnotredddit: SubNotRedditInfo, val voted: VoteStatus, val time: String, val imgs: List<String>, val text: String) : PostContent()

    @Serializable
    data class TextPost(val postId: Int, val title: String, val score: Int, val comments: Int, val user: UserInfo, val subnotredddit: SubNotRedditInfo, val voted: VoteStatus, val time: String, val text: String) : PostContent()
}