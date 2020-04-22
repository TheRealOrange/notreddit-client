package com.therealorange.notreddit.client.data

import kotlinx.serialization.Serializable
import org.joda.time.DateTime

@Serializable
data class UserData(val id: Int, val username: String)