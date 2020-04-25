package com.therealorange.notreddit.client

import com.therealorange.notreddit.client.Client.sendMessage
import com.therealorange.notreddit.client.data.Data
import com.therealorange.notreddit.client.data.UserType
import com.therealorange.notreddit.client.data.objects.PostContent
import com.therealorange.notreddit.client.data.objects.SubNotRedditInfo
import com.therealorange.notreddit.client.data.objects.UserInfo
import com.therealorange.notreddit.util.UUIDv5
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.security.MessageDigest

object WebSocket {
    val postReqCallbacks = mutableMapOf<String, ((p: PostContent) -> Unit)>()
    val subNotRedditReqCallbacks = mutableMapOf<String, ((p: SubNotRedditInfo) -> Unit)>()
    val userReqCallbacks = mutableMapOf<String, ((u: UserInfo) -> Unit)>()

    fun sendAuth(username: String, pwd: String): Job {
        val hashedpwd = hashString("SHA-256", username + pwd)
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.Auth(username, hashedpwd))
        }
    }

    fun sendAddUser(username: String, pwd: String): Job {
        val hashedpwd = hashString("SHA-256", username + pwd)
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.AddUser(username, hashedpwd))
        }
    }

    fun sendAddPost(post: PostContent): Job {
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.AddPost(post))
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
            Client.sess.sendMessage(Data.GetSubNotReddit(userId, requestID))
        }
    }

    fun messsageHandler(message: Data) {
        when(message) {
            is Data.Authenticated->{
                User.username = message.user.username
                User.usertype = UserType.USER_NORMAL
                User.userinfo = message.user
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