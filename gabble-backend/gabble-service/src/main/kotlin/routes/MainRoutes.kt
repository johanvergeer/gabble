package routes

import com.google.inject.Inject
import com.redgyro.dto.gabbles.GabbleDto
import exception.AuthenticationException
import exception.AuthorizationException
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
import service.GabbleService

class MainRoutes @Inject constructor(
    application: Application,
    private val gabbleService: GabbleService) {

    init {
        application.
            routing {
                post("/") {
                    val gabble = call.receive<GabbleDto>()
                    call.respond(gabbleService.create(gabble))
                }

                get("/{userId}") {
                    val userId: String? = call.parameters["userId"]

                    if (userId == null) call.respond(HttpStatusCode.BadRequest, "userId cannot be null")
                    else call.respond(gabbleService.findByUserId(userId))
                }

                get("/tags") {
                    call.respond(gabbleService.findAllGabbleTags())
                }

                get("/foo") {
                    call.respond("bar")
                }

                install(StatusPages) {
                    exception<AuthenticationException> { call.respond(HttpStatusCode.Unauthorized) }
                    exception<AuthorizationException> { call.respond(HttpStatusCode.Forbidden) }
                }
            }
    }
}