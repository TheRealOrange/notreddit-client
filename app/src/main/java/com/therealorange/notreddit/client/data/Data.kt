package com.therealorange.notreddit.client.data

import kotlinx.serialization.Serializable
import org.joda.time.DateTime
import org.joda.time.LocalDate

@Serializable
sealed class Data {
    @Serializable
    data class Error(val code: Status) : Data() //server

    @Serializable
    data class Success(val code: Status) : Data() //server

    @Serializable
    data class Auth(val user: String, val hashedpwd: String) : Data() //client

    @Serializable
    data class User(val user: String, val type: UserType) : Data() //server

    @Serializable
    data class AddUser(val user: String, val hashedpwd: String) : Data() //client
}