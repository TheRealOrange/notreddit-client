package com.therealorange.notreddit.data.objects

import kotlinx.serialization.Serializable

@Serializable
data class SubNotReddit(val subNotReddditId: Int, val name: String, val description: String, val about: String, val icon : String, val banner: String, val subscribed: Int)