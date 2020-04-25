package com.therealorange.notreddit.util

import android.widget.Button
import com.therealorange.notreddit.client.data.objects.PostContent

sealed class PostItem{
    data class TextPostItem(val content: PostContent.TextPost, val subNotRedditClicked: (() -> Unit), val userClicked: (() -> Unit), val menuClicked: (() -> Unit), val titleClicked: (() -> Unit), val imageClicked: (() -> Unit), val downvoteClicked: ((btn: Button) -> Unit), val commentClicked: (() -> Unit), val shareClicked: (() -> Unit))
}