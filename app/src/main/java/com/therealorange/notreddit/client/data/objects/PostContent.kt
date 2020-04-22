package com.therealorange.notreddit.data.objects

import kotlinx.serialization.Serializable

@Serializable
open class PostContent {
    @Serializable
    data class ImgPost(val postId: Int, val title: String, val score: Int, val comments: Int, val user: UserInfo, val subnotredddit: SubNotReddit, val time: String, val imgs: List<String>) : PostContent()

    @Serializable
    data class ImgTextPost(val postId: Int, val title: String, val score: Int, val comments: Int, val user: UserInfo, val subnotredddit: SubNotReddit, val time: String, val imgs: List<String>, val text: String) : PostContent()

    @Serializable
    data class TextPost(val postId: Int, val title: String, val score: Int, val comments: Int, val user: UserInfo, val subnotredddit: SubNotReddit, val time: String, val text: String) : PostContent()
}