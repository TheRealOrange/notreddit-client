package com.therealorange.notreddit.client

import com.therealorange.notreddit.client.data.UserType
import com.therealorange.notreddit.client.data.objects.UserInfo

object User {
    lateinit var username: String
    var usertype = UserType.USER_UNAUTHENTICATED
    lateinit var userinfo: UserInfo
}