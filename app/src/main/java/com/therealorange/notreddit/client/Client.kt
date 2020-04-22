package com.therealorange.notreddit.client

import com.therealorange.notreddit.client.data.Data
import com.therealorange.notreddit.client.data.Status
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.ClientWebSocketSession
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.ws
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.readText
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecodingException

object Client {
    private lateinit var client: HttpClient
    val connectCallbacks = mutableListOf<() -> Unit>()
    val disconnectCallbacks = mutableListOf<() -> Unit>()
    val statusCallbacks = mutableMapOf<Status, () -> Unit>()

    lateinit var sess: ClientWebSocketSession

    fun init(): Job {
        return GlobalScope.launch { websocketConnect("10.0.2.2", 8080, "/voting") }
    }

    /*fun clearStatusCallbacks()
    {
        for (i in statusCallbacks.keys)
            statusCallbacks.remove(i)
    }*/

    @KtorExperimentalAPI
    suspend fun websocketConnect(wshost: String, wsport: Int, route: String) {
        client = HttpClient { install(WebSockets) }
        client.ws(
            host = wshost,
            port = wsport,
            path = route,
            block = ::connectionHandler
        )
    }

    private suspend fun connectionHandler(connection: ClientWebSocketSession) {
        this.sess = connection
        onConnect()
        for (frame in connection.incoming) {
            if (frame !is Frame.Text) return
            val message =
                try {
                    Json.parse(Data.serializer(), frame.readText())
                } catch (e: JsonDecodingException) {
                    println("JSON: ${frame.readText()}")
                    return println("JSON decoding error: $e")
                }
            handle(message)
        }
        onDisconnect()
    }

    private fun handle(message: Data) {
        GlobalScope.launch {
            when (message) {
                is Data.Error -> {
                    println("Error occurred: ${message.code}")
                    synchronized(statusCallbacks) { if (statusCallbacks.containsKey(message.code)) statusCallbacks[message.code]!!.invoke() }
                }
                is Data.Success-> {
                    print("Success: ${message.code}")
                    synchronized(statusCallbacks) { if (statusCallbacks.containsKey(message.code)) statusCallbacks[message.code]!!.invoke() }
                }
                else-> WebSocket.messsageHandler(message)
            }
        }
    }

    private fun onConnect() = connectCallbacks.forEach { it() }

    private fun onDisconnect() = disconnectCallbacks.forEach { it() }

    internal suspend fun WebSocketSession.sendMessage(message: Data) {
        this.outgoing.send(Frame.Text(Json.stringify(Data.serializer(), message)))
    }
}