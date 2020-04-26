package com.therealorange.notreddit.client

import android.graphics.Bitmap
import com.therealorange.notreddit.client.Client.sendMessage
import com.therealorange.notreddit.client.Client.statusCallbacks
import com.therealorange.notreddit.client.data.*
import com.therealorange.notreddit.client.data.objects.CommentContent
import com.therealorange.notreddit.client.data.objects.PostContent
import com.therealorange.notreddit.client.data.objects.SubNotRedditInfo
import com.therealorange.notreddit.client.data.objects.UserInfo
import com.therealorange.notreddit.util.DrawerNavigation
import com.therealorange.notreddit.util.ImageUtils.encodeToBase64
import com.therealorange.notreddit.util.UUIDv5
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.security.MessageDigest

object WebSocket {
    val postReqCallbacks = mutableMapOf<String, ((p: PostContent) -> Unit)>()
    val subNotRedditReqCallbacks = mutableMapOf<String, ((p: SubNotRedditInfo) -> Unit)>()
    val userReqCallbacks = mutableMapOf<String, ((u: UserInfo) -> Unit)>()
    val feedReqCallbacks = mutableMapOf<String, ((f: List<Int>) -> Unit)>()
    val subNotRedditPostReqCallbacks = mutableMapOf<String, ((f: List<Int>) -> Unit)>()
    val userPostReqCallbacks = mutableMapOf<String, ((f: List<Int>) -> Unit)>()
    val commentReqCallbacks = mutableMapOf<String, ((f: List<Int>) -> Unit)>()
    val commentContentReqCallbacks = mutableMapOf<String, ((c: CommentContent) -> Unit)>()
    val postVoteCallbacks = mutableMapOf<String, ((b: Boolean) -> Unit)>()
    val commentVoteCallbacks = mutableMapOf<String, ((b: Boolean) -> Unit)>()
    val subNotRedditSubscribeCallbacks = mutableMapOf<String, ((b: Boolean) -> Unit)>()

    fun sendAuth(username: String, pwd: String, success: (() -> Unit) = {}, failure: (() -> Unit) = {}): Job {
        val hashedpwd = hashString("SHA-256", username + pwd)
        statusCallbacks[Status.AUTH_SUCCESS] = {
            success.invoke()
            statusCallbacks[Status.AUTH_SUCCESS] = {}
        }
        statusCallbacks[Status.AUTH_ERROR] = {
            failure.invoke()
            statusCallbacks[Status.AUTH_ERROR] = {}
        }
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.Auth(username, hashedpwd))
        }
    }

    fun sendAddUser(username: String, pwd: String, icon: Bitmap, banner: Bitmap, success: (() -> Unit) = {}, failure: (() -> Unit) = {}): Job {
        val hashedpwd = hashString("SHA-256", username + pwd)
        statusCallbacks[Status.ADD_USER_SUCCESS] = {
            success.invoke()
            statusCallbacks[Status.ADD_USER_SUCCESS] = {}
        }
        statusCallbacks[Status.ADD_USER_ERROR] = {
            failure.invoke()
            statusCallbacks[Status.ADD_USER_ERROR] = {}
        }
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.AddUser(username, hashedpwd, encodeToBase64(icon)!!, encodeToBase64(banner)!!))
        }
    }

    fun sendGetPost(postId: Int, callback: ((p: PostContent) -> Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        postReqCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.GetPost(postId, requestID))
        }
    }

    fun sendGetSubNotReddit(subNotRedditId: Int, callback: ((s: SubNotRedditInfo) -> Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        subNotRedditReqCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.GetSubNotReddit(subNotRedditId, requestID))
        }
    }

    fun sendGetUser(userId: Int, callback: ((u: UserInfo) -> Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        userReqCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.GetUser(userId, requestID))
        }
    }

    fun sendGetFeed(sort: SortType, callback: (f: List<Int>) -> Unit): Job {
        val requestID = UUIDv5.randomUUID().toString()
        feedReqCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.GetFeed(sort, requestID))
        }
    }

    fun sendGetUserPosts(userId: Int, sort: SortType, callback: ((f: List<Int>) -> Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        userPostReqCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.GetUserPosts(userId, sort, requestID))
        }
    }

    fun sendGetSubNotRedditPosts(subNotRedditId: Int, sort: SortType, callback: ((f: List<Int>) -> Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        subNotRedditPostReqCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.GetSubNotRedditPosts(subNotRedditId, sort, requestID))
        }
    }

    fun sendGetPostComments(postId: Int, sort: SortType, callback: ((f: List<Int>) -> Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        commentReqCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.GetPostComments(postId, sort, requestID))
        }
    }

    fun sendGetCommentComments(postId: Int, sort: SortType, callback: ((f: List<Int>) -> Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        commentReqCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.GetCommentComments(postId, sort, requestID))
        }
    }

    fun sendGetComment(commentId: Int, callback: (c: CommentContent) -> Unit): Job{
        val requestID = UUIDv5.randomUUID().toString()
        commentContentReqCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.GetComment(commentId, requestID))
        }
    }

    fun sendAddSubNotReddit(subNotReddit: SubNotRedditInfo, success: (() -> Unit) = {}, failure: (() -> Unit) = {}): Job {
        statusCallbacks[Status.ADD_SUBNOTREDDIT_SUCCESS] = {
            success.invoke()
            statusCallbacks[Status.ADD_SUBNOTREDDIT_SUCCESS] = {}
        }
        statusCallbacks[Status.ADD_SUBNOTREDDIT_ERROR] = {
            failure.invoke()
            statusCallbacks[Status.ADD_SUBNOTREDDIT_ERROR] = {}
        }
        return GlobalScope.launch { Client.sess.sendMessage(Data.AddSubNotReddit(subNotReddit)) }
    }

    fun sendAddPost(post: PostContent, success: (() -> Unit) = {}, failure: (() -> Unit) = {}): Job {
        statusCallbacks[Status.ADD_POST_SUCCESS] = {
            success.invoke()
            statusCallbacks[Status.ADD_POST_SUCCESS] = {}
        }
        statusCallbacks[Status.ADD_POST_ERROR] = {
            failure.invoke()
            statusCallbacks[Status.ADD_POST_ERROR] = {}
        }
        return GlobalScope.launch { Client.sess.sendMessage(Data.AddPost(post)) }
    }

    fun sendAddComment(comment: CommentContent, success: (() -> Unit) = {}, failure: (() -> Unit) = {}): Job {
        statusCallbacks[Status.ADD_COMMENT_SUCCESS] = {
            success.invoke()
            statusCallbacks[Status.ADD_COMMENT_SUCCESS] = {}
        }
        statusCallbacks[Status.ADD_COMMENT_ERROR] = {
            failure.invoke()
            statusCallbacks[Status.ADD_COMMENT_ERROR] = {}
        }
        return GlobalScope.launch { Client.sess.sendMessage(Data.AddComment(comment)) }
    }

    fun sendPostVote(postId: Int, voteStatus: VoteStatus, callback: ((b: Boolean) -> Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        postVoteCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.PostVote(postId, voteStatus, requestID))
        }
    }

    fun sendCommentVote(commentId: Int, voteStatus: VoteStatus, callback: ((b: Boolean) -> Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        commentVoteCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.CommentVote(commentId, voteStatus, requestID))
        }
    }

    fun sendSubNotRedditSubscribe(subscribe: Boolean, subNotRedditId: Int, callback: ((b: Boolean)->Unit)): Job {
        val requestID = UUIDv5.randomUUID().toString()
        subNotRedditSubscribeCallbacks[requestID] = callback
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.SubNotRedditSubscribe(subscribe, subNotRedditId, requestID))
        }
    }

    fun sendUnauth(success: (() -> Unit) = {}, failure: (() -> Unit) = {}): Job {
        statusCallbacks[Status.UNAUTH_SUCCESS] = {
            success.invoke()
            statusCallbacks[Status.UNAUTH_SUCCESS] = {}
        }
        statusCallbacks[Status.UNAUTH_ERROR] = {
            failure.invoke()
            statusCallbacks[Status.UNAUTH_ERROR] = {}
        }
        return GlobalScope.launch { Client.sess.sendMessage(Data.Unauth(User.userinfo.userId)) }
    }

    fun messsageHandler(message: Data) {
        when(message) {
            is Data.Authenticated->{
                User.username = message.user.username
                User.usertype = UserType.USER_NORMAL
                User.userinfo = message.user
                DrawerNavigation.login()
            }
            is Data.Unauthenticated->{
                User.username = ""
                User.usertype = UserType.USER_UNAUTHENTICATED
                DrawerNavigation.logout()
            }
            is Data.Post->{
                if (postReqCallbacks.containsKey(message.uuid)) {
                    postReqCallbacks[message.uuid]?.invoke(message.post)
                    postReqCallbacks.remove(message.uuid)
                }
            }
            is Data.SubNotReddit->{
                if (subNotRedditReqCallbacks.containsKey(message.uuid)) {
                    subNotRedditReqCallbacks[message.uuid]?.invoke(message.subNotReddit)
                    subNotRedditReqCallbacks.remove(message.uuid)
                }
            }
            is Data.User->{
                if (userReqCallbacks.containsKey(message.uuid)) {
                    userReqCallbacks[message.uuid]?.invoke(message.user)
                    userReqCallbacks.remove(message.uuid)
                }
            }
            is Data.UserPosts->{
                if (userPostReqCallbacks.containsKey(message.uuid)) {
                    userPostReqCallbacks[message.uuid]?.invoke(message.postIds)
                    userPostReqCallbacks.remove(message.uuid)
                }
            }
            is Data.SubNotRedditPosts->{
                if (subNotRedditPostReqCallbacks.containsKey(message.uuid)) {
                    subNotRedditPostReqCallbacks[message.uuid]?.invoke(message.postIds)
                    subNotRedditPostReqCallbacks.remove(message.uuid)
                }
            }
            is Data.Feed->{
                if (feedReqCallbacks.containsKey(message.uuid)) {
                    feedReqCallbacks[message.uuid]?.invoke(message.postIds)
                    feedReqCallbacks.remove(message.uuid)
                }
            }
            is Data.Comments->{
                if(commentReqCallbacks.containsKey(message.uuid)) {
                    commentReqCallbacks[message.uuid]?.invoke(message.commentIds)
                    commentReqCallbacks.remove(message.uuid)
                }
            }
            is Data.Comment->{
                if(commentContentReqCallbacks.containsKey(message.uuid)) {
                    commentContentReqCallbacks[message.uuid]?.invoke(message.comment)
                    commentContentReqCallbacks.remove(message.uuid)
                }
            }
            is Data.PostVoteStatus->{
                if(postVoteCallbacks.containsKey(message.uuid)) {
                    postVoteCallbacks[message.uuid]?.invoke(message.status)
                    postVoteCallbacks.remove(message.uuid)
                }
            }
            is Data.CommentVoteStatus->{
                if(commentVoteCallbacks.containsKey(message.uuid)) {
                    commentVoteCallbacks[message.uuid]?.invoke(message.status)
                    commentVoteCallbacks.remove(message.uuid)
                }
            }
            is Data.SubNotRedditSubscribeResult->{
                if (message.success) {
                    User.userinfo = message.user
                }
                if (subNotRedditSubscribeCallbacks.containsKey(message.uuid)) {
                    subNotRedditSubscribeCallbacks[message.uuid]?.invoke(message.success)
                    subNotRedditSubscribeCallbacks.remove(message.uuid)
                }
            }
        }
    }

    private fun hashString(type: String, input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }
}