package com.redgyro.gabblesservice.routes

import com.google.inject.Inject
import com.redgyro.dto.gabbles.GabbleDto
import com.redgyro.gabblesservice.exception.AuthenticationException
import com.redgyro.gabblesservice.exception.AuthorizationException
import com.redgyro.gabblesservice.service.GabbleService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

class MainRoutes @Inject constructor(
    application: Application,
    private val gabbleService: GabbleService) {

    init {
        application.routing {
            post("/") {
                val gabble = call.receive<GabbleDto>()
                call.respond(gabbleService.create(gabble))
            }

            get("/{userId}") {
                val userId: String? = call.parameters["userId"]

                if (userId == null) call.respond(HttpStatusCode.BadRequest, "userId cannot be null")
                else call.respond(gabbleService.findByUserId(userId))
            }

            get("/{userId}/mentions") {
                val userId: String? = call.parameters["userId"]

                if (userId == null) call.respond(HttpStatusCode.BadRequest, "userId cannot be null")
                else call.respond(gabbleService.findMentionedInForUser(userId))
            }

            get("/tags") {
                call.respond(gabbleService.findAllGabbleTags())
            }

            install(StatusPages) {
                exception<AuthenticationException> { call.respond(HttpStatusCode.Unauthorized) }
                exception<AuthorizationException> { call.respond(HttpStatusCode.Forbidden) }
            }
        }
    }
}