package com.therealorange.notreddit.client.data.objects

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(val userId: Int, val username: String, val karma: Int, val icon: String, val banner: String, val subscribed: List<Int>)