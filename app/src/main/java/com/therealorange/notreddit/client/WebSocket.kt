package com.therealorange.notreddit.client

import android.app.Activity
import android.location.Location
import com.therealorange.notreddit.client.data.Data
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.security.MessageDigest
import com.therealorange.notreddit.client.Client.sendMessage

object WebSocket {

    fun sendAuth(username: String, pwd: String): Job {
        val hashedpwd = hashString("SHA-256", username + pwd)
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.Auth(username, hashedpwd))
        }
    }

    fun sendAddUser(username: String, pwd: String): Job {
        val hashedpwd = hashString("SHA-256", username + pwd)
        return GlobalScope.launch {
            Client.sess.sendMessage(Data.AddUser(username, hashedpwd))
        }
    }

    fun messsageHandler(message: Data) {
        when(message) {
            is Data.User->{
                User.username = message.user
                User.usertype = message.type
            }
        }
    }

    private fun hashString(type: String, input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }

        return result.toString()
    }
}