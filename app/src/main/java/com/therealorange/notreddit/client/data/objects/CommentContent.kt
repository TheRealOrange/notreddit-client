package com.therealorange.notreddit.client.data.objects

import com.therealorange.notreddit.client.data.objects.UserInfo
import com.therealorange.notreddit.client.data.VoteStatus

data class CommentContent(val user: UserInfo, val postId: Int, val content: String, val parentCommentId: Int, val score: Int, val voted: VoteStatus, val time: String)