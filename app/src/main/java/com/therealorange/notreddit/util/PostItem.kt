package com.therealorange.notreddit.util

import android.widget.Button
import com.therealorange.notreddit.client.data.objects.PostContent

sealed class PostItem{
    data class TextPostItem(
        val content: PostContent.TextPost,
        val subNotRedditClicked: (() -> Unit) = {},
        val userClicked: (() -> Unit) = {},
        val menuClicked: (() -> Unit) = {},
        val titleClicked: (() -> Unit) = {},
        val textClicked: (() -> Unit) = {},
        val upvoteClicked: ((btn: Button) -> Unit) = {},
        val downvoteClicked: ((btn: Button) -> Unit) = {},
        val commentClicked: (() -> Unit) = {},
        val shareClicked: (() -> Unit) = {}) : PostItem()
    data class ImgPostItem(
        val content: PostContent.ImgPost,
        val subNotRedditClicked: (() -> Unit) = {},
        val userClicked: (() -> Unit) = {},
        val menuClicked: (() -> Unit) = {},
        val titleClicked: (() -> Unit) = {},
        val imageClicked: (() -> Unit) = {},
        val upvoteClicked: ((btn: Button) -> Unit) = {},
        val downvoteClicked: ((btn: Button) -> Unit) = {},
        val commentClicked: (() -> Unit) = {},
        val shareClicked: (() -> Unit) = {}) : PostItem()
    data class ImgTextPostItem(
        val content: PostContent.ImgTextPost,
        val subNotRedditClicked: (() -> Unit) = {},
        val userClicked: (() -> Unit) = {},
        val menuClicked: (() -> Unit) = {},
        val titleClicked: (() -> Unit) = {},
        val textClicked: (() -> Unit) = {},
        val imageClicked: (() -> Unit) = {},
        val upvoteClicked: ((btn: Button) -> Unit) = {},
        val downvoteClicked: ((btn: Button) -> Unit) = {},
        val commentClicked: (() -> Unit) = {},
        val shareClicked: (() -> Unit) = {}) : PostItem()
}