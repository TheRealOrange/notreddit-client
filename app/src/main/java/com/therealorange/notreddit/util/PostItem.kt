package com.therealorange.notreddit.util

import android.widget.Button
import com.therealorange.notreddit.client.data.objects.PostContent

sealed class PostItem{
    data class TextPostItem(
        val content: PostContent.TextPost,
        val subNotRedditClicked: ((p: PostItem) -> Unit) = {},
        val userClicked: ((p: PostItem) -> Unit) = {},
        val menuClicked: ((p: PostItem) -> Unit) = {},
        val titleClicked: ((p: PostItem) -> Unit) = {},
        val textClicked: ((p: PostItem) -> Unit) = {},
        val upvoteClicked: ((btn: Button, p: PostItem) -> Unit) = { button: Button, postItem: PostItem -> },
        val downvoteClicked: ((btn: Button, p: PostItem) -> Unit) = { button: Button, postItem: PostItem -> },
        val commentClicked: ((p: PostItem) -> Unit) = {},
        val shareClicked: ((p: PostItem) -> Unit) = {}) : PostItem()
    data class ImgPostItem(
        val content: PostContent.ImgPost,
        val subNotRedditClicked: ((p: PostItem) -> Unit) = {},
        val userClicked: ((p: PostItem) -> Unit) = {},
        val menuClicked: ((p: PostItem) -> Unit) = {},
        val titleClicked: ((p: PostItem) -> Unit) = {},
        val imageClicked: ((p: PostItem) -> Unit) = {},
        val upvoteClicked: ((btn: Button, p: PostItem) -> Unit) = { button: Button, postItem: PostItem -> },
        val downvoteClicked: ((btn: Button, p: PostItem) -> Unit) = { button: Button, postItem: PostItem -> },
        val commentClicked: ((p: PostItem) -> Unit) = {},
        val shareClicked: ((p: PostItem) -> Unit) = {}) : PostItem()
    data class ImgTextPostItem(
        val content: PostContent.ImgTextPost,
        val subNotRedditClicked: ((p: PostItem) -> Unit) = {},
        val userClicked: ((p: PostItem) -> Unit) = {},
        val menuClicked: ((p: PostItem) -> Unit) = {},
        val titleClicked: ((p: PostItem) -> Unit) = {},
        val textClicked: ((p: PostItem) -> Unit) = {},
        val imageClicked: ((p: PostItem) -> Unit) = {},
        val upvoteClicked: ((btn: Button, p: PostItem) -> Unit) = { button: Button, postItem: PostItem -> },
        val downvoteClicked: ((btn: Button, p: PostItem) -> Unit) = { button: Button, postItem: PostItem -> },
        val commentClicked: ((p: PostItem) -> Unit) = {},
        val shareClicked: ((p: PostItem) -> Unit) = {}) : PostItem()
}