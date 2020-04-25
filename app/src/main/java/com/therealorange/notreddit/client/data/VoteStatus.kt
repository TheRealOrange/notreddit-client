package com.therealorange.notreddit.client.data

import kotlinx.serialization.Serializable

@Serializable
enum class VoteStatus {
    ERROR, UPVOTE, DOWNVOTE, NONE
}