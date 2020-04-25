package com.therealorange.notreddit.client.data

import com.therealorange.notreddit.client.data.objects.PostContent
import com.therealorange.notreddit.client.data.objects.SubNotRedditInfo
import com.therealorange.notreddit.client.data.objects.UserInfo
import kotlinx.serialization.Serializable

@Serializable
sealed class Data {
    @Serializable
    data class Error(val code: Status) : Data() //server

    @Serializable
    data class Success(val code: Status) : Data() //server

    @Serializable
    data class Auth(val user: String, val hashedpwd: String) : Data() //client

    @Serializable
    data class Authenticated(val user: UserInfo) : Data() //server

    @Serializable
    data class AddUser(val user: String, val hashedpwd: String) : Data() //client

    @Serializable
    data class GetUser(val userId: Int, val uuid: String) : Data() //client

    @Serializable
    data class User(val user: UserInfo, val uuid: String) : Data() //server

    @Serializable
    data class UpdateUser(val userInfo: UserInfo) : Data() //client

    @Serializable
    data class AddSubNotReddit(val subNotReddit: SubNotRedditInfo) : Data() //client

    @Serializable
    data class GetSubNotReddit(val subNotRedditId: Int, val uuid: String) : Data() //client

    @Serializable
    data class SubNotReddit(val subNotReddit: SubNotRedditInfo, val uuid: String) : Data() //server

    @Serializable
    data class UpdateSubNotReddit(val subNotReddit: SubNotRedditInfo) : Data() //server

    @Serializable
    data class AddPost(val post: PostContent) : Data() //client

    @Serializable
    data class GetPost(val postId: Int, val uuid: String) : Data() //client

    @Serializable
    data class Post(val post: PostContent, val uuid: String) : Data() //server

    object Ping : Data()

    object Pong : Data()
}