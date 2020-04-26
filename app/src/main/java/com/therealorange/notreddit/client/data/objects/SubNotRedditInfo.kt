package com.therealorange.notreddit.client.data.objects

import kotlinx.serialization.Serializable

@Serializable
data class SubNotRedditInfo(val subNotRedditId: Int, val name: String, val description: String, val about: String, val icon : String, val banner: String, val subscribed: Int, val userId: Int)