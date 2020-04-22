package com.therealorange.notreddit.client

import com.therealorange.notreddit.client.data.UserType

object User {
    lateinit var username: String
    var usertype = UserType.USER_UNAUTHENTICATED
}