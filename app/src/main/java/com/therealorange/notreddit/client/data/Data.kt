package com.therealorange.notreddit.client.data

import com.therealorange.notreddit.client.data.objects.CommentContent
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
    data class Unauthenticated(val user: UserInfo) : Data() //server

    @Serializable
    data class AddUser(val user: String, val hashedpwd: String, val icon: String, val banner: String) : Data() //client

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

    @Serializable
    data class GetFeed(val sort: SortType, val uuid: String) : Data() //client

    @Serializable
    data class Feed(val postIds: List<Int>, val uuid: String) : Data() //server

    @Serializable
    data class GetSubNotRedditPosts(val subNotRedditId: Int, val sort: SortType, val uuid: String) : Data() //client

    @Serializable
    data class SubNotRedditPosts(val postIds: List<Int>, val uuid: String) : Data() //server

    @Serializable
    data class GetUserPosts(val userId: Int, val sort: SortType, val uuid: String) : Data() //client

    @Serializable
    data class UserPosts(val postIds: List<Int>, val uuid: String) : Data() //server

    @Serializable
    data class GetPostComments(val postId: Int, val sort: SortType, val uuid: String) : Data()

    @Serializable
    data class GetCommentComments(val commentId: Int, val sort: SortType, val uuid: String) : Data()

    @Serializable
    data class Comments(val commentIds: List<Int>, val uuid: String) : Data()

    @Serializable
    data class GetComment(val commentId: Int, val uuid: String) : Data()

    @Serializable
    data class Comment(val comment: CommentContent, val uuid: String) : Data()

    @Serializable
    data class AddComment(val comment: CommentContent) : Data()

    @Serializable
    data class PostVote(val postId: Int, val voteStatus: VoteStatus, val uuid: String) : Data()

    @Serializable
    data class CommentVote(val commentId: Int, val voteStatus: VoteStatus, val uuid: String) : Data()

    @Serializable
    data class PostVoteStatus(val status: Boolean, val uuid: String) : Data()

    @Serializable
    data class CommentVoteStatus(val status: Boolean, val uuid: String) : Data()

    @Serializable
    data class SubNotRedditSubscribe(val subscribe: Boolean, val subNotRedditId: Int, val uuid: String) : Data()

    @Serializable
    data class SubNotRedditSubscribeResult(val success: Boolean, val user: UserInfo, val uuid: String) : Data()

    @Serializable
    data class Unauth(val userId: Int) : Data()

    object Ping : Data()

    object Pong : Data()
}