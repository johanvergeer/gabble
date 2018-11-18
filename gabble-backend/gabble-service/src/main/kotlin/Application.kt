package com.redgyro

import com.redgyro.dto.gabbles.GabbleDto
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import io.ktor.auth.jwt.jwt
import io.ktor.gson.gson
import java.text.DateFormat
import java.time.LocalDateTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.DevelopmentEngine.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val gabbleService: GabbleService = InMemoryGabbleService()

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(Authentication) {

    }

    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }

    routing {
        get("/{userId}") {
            val userId: String? = call.parameters["userId"]

            if (userId == null) call.respond(HttpStatusCode.BadRequest, "userId cannot be null")
            else call.respond(gabbleService.findByUserId(userId))
        }

        install(StatusPages) {
            exception<AuthenticationException> { call.respond(HttpStatusCode.Unauthorized) }
            exception<AuthorizationException> { call.respond(HttpStatusCode.Forbidden) }
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

interface GabbleService {
    fun findByUserId(userId: String): Set<GabbleDto>
}

class InMemoryGabbleService : GabbleService {
    private val gabbles = setOf(
        GabbleDto(id = "1000", text = "Gabble 1 from user johan_vergeer", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now().minusDays(1)),
        GabbleDto(id = "1001", text = "Gabble 2 from user johan_vergeer", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now().minusHours(20)),
        GabbleDto(id = "1002", text = "Gabble 3 from user johan_vergeer", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now().minusHours(5)),
        GabbleDto(id = "1003", text = "Gabble 1 from user floris_bosch", createdById = "00ugl9afjiwNub6yt0h8", createdOn = LocalDateTime.now().minusHours(15)),
        GabbleDto(id = "1004", text = "Gabble 2 from user floris_bosch", createdById = "00ugl9afjiwNub6yt0h8", createdOn = LocalDateTime.now().minusHours(8)),
        GabbleDto(id = "1005", text = "Gabble 1 from user matt_damon", createdById = "00ugl9afjiwNub6yt0h9", createdOn = LocalDateTime.now().minusHours(7)),
        GabbleDto(id = "1006", text = "Gabble 2 from user matt_damon", createdById = "00ugl9afjiwNub6yt0h9", createdOn = LocalDateTime.now().minusHours(18)),
        GabbleDto(id = "1007", text = "Gabble 1 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdOn = LocalDateTime.now().minusHours(19)),
        GabbleDto(id = "1008", text = "Gabble 2 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdOn = LocalDateTime.now().minusHours(21)),
        GabbleDto(id = "1009", text = "Gabble 3 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdOn = LocalDateTime.now().minusHours(35)),
        GabbleDto(id = "1010", text = "Gabble 1 from user matthew_murdock", createdById = "00ugl9afjiwNub6yth11", createdOn = LocalDateTime.now().minusHours(41))
    )

    override fun findByUserId(userId: String): Set<GabbleDto> {
        return this.gabbles.filter { dto -> dto.createdById == userId }.toSet()
    }
}
