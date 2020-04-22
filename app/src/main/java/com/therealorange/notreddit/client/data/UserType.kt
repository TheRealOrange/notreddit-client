package com.therealorange.notreddit.client.data
import kotlinx.serialization.Serializable

@Serializable
enum class UserType {
    USER_ADMIN, USER_NORMAL, USER_UNAUTHENTICATED
}