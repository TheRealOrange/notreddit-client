package com.therealorange.notreddit.client.data

import kotlinx.serialization.Serializable

@Serializable
data class UserData(val id: Int, val username: String)