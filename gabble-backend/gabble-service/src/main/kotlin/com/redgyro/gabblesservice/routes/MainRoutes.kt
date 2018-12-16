package com.redgyro.gabblesservice.routes

import com.google.inject.Inject
import com.redgyro.dto.gabbles.GabbleDto
import com.redgyro.gabblesservice.GabbleSession
import com.redgyro.gabblesservice.OktaOAuthNonceSession
import com.redgyro.gabblesservice.events.GabbleCreateEvent
import com.redgyro.gabblesservice.gabbleGson
import com.redgyro.gabblesservice.service.GabbleService
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.websocket.webSocket
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.runBlocking

class MainRoutes @Inject constructor(
    application: Application,
    private val gabbleService: GabbleService) {

    private val gson = gabbleGson()

    private fun ApplicationCall.getUserId() = this.parameters["userId"]
    private fun ApplicationCall.getGabblesSession() = this.sessions.get<GabbleSession>()

    init {
        application.routing {
            post("/") {
                val gabble = call.receive<GabbleDto>()
                call.respond(gabbleService.create(gabble))
            }

            get("/{userId}") {
                val userId: String? = call.getUserId()

                if (userId == null) call.respond(HttpStatusCode.BadRequest, "userId cannot be null")
                else call.respond(gabbleService.findByUserId(userId))
            }

            get("/{userId}/mentions") {
                val userId: String? = call.getUserId()

                if (userId == null) call.respond(HttpStatusCode.BadRequest, "userId cannot be null")
                else call.respond(gabbleService.findMentionedInForUser(userId))
            }

            get("/tags") {
                call.respond(gabbleService.findAllGabbleTags())
            }

            // Create a new session for the user
            post("/users/sessions/{userId}") {
                val userId: String? = call.getUserId()
                val oktaSession = call.getGabblesSession()
                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "userId path variable is required")
                } else {
                    call.sessions.set(GabbleSession(userId = userId))
                    call.respond(HttpStatusCode.OK)
                }
            }

            webSocket("/mentions") {
                println("onConnect")
                val session = call.getGabblesSession()

//                if (session == null) {
//                    close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
//                    return@webSocket
//                }

                try {
                    GabbleCreateEvent on {
                        val gabbleDto = it.gabbleDto
//                        if (gabbleDto.mentions.contains(session.userId)) {
                            runBlocking { outgoing.send(Frame.Text(gson.toJson(it.gabbleDto))) }
//                        }
                    }
                    while (true) {
                        val frame = incoming.receive()
                        when (frame) {
                            is Frame.Text -> {
                                val text = frame.readText()
                                outgoing.send(Frame.Text("You said: $text"))
                            }
                        }
                    }
                } catch (e: ClosedReceiveChannelException) {
                    println("onClose ${closeReason.await()}")
                } catch (e: Throwable) {
                    println("onError ${closeReason.await()}")
                    e.printStackTrace()
                }
            }
        }
    }
}