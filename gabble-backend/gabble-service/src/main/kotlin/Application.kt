package com.redgyro

import com.google.gson.*
import com.redgyro.dto.gabbles.GabbleDto
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
            setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeConverter())
            setPrettyPrinting()
        }
    }

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

        install(StatusPages) {
            exception<AuthenticationException> { call.respond(HttpStatusCode.Unauthorized) }
            exception<AuthorizationException> { call.respond(HttpStatusCode.Forbidden) }
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

fun randomUUID() = UUID.randomUUID().toString()

class LocalDateTimeConverter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        return LocalDateTime.parse(json.asString, FORMATTER)
    }

    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }
}

interface GabbleService {
    fun findByUserId(userId: String): Set<GabbleDto>

    fun create(gabbleDto: GabbleDto): GabbleDto

    fun findAllGabbleTags(): Set<String>
}

class InMemoryGabbleService : GabbleService {

    private val gabbles = mutableSetOf<GabbleDto>()

    init {
        create(GabbleDto(text = "Gabble 1 from user johan_vergeer with tag #tag1", createdById = "00ugl9afjiwNub6yt0h7", createdByUsername = "johan_vergeer", createdOn = LocalDateTime.now().minusDays(1)))
        create(GabbleDto(text = "Gabble 2 from user johan_vergeer with #tag1 and #tag2", createdById = "00ugl9afjiwNub6yt0h7", createdByUsername = "johan_vergeer", createdOn = LocalDateTime.now().minusHours(20)))
        create(GabbleDto(text = "Gabble 3 from user johan_vergeer", createdById = "00ugl9afjiwNub6yt0h7", createdByUsername = "johan_vergeer", createdOn = LocalDateTime.now().minusHours(5)))
        create(GabbleDto(text = "Gabble 1 from user floris_bosch", createdById = "00ugl9afjiwNub6yt0h8", createdByUsername = "floris_bosch", createdOn = LocalDateTime.now().minusHours(15)))
        create(GabbleDto(text = "Gabble 2 from user floris_bosch", createdById = "00ugl9afjiwNub6yt0h8", createdByUsername = "floris_bosch", createdOn = LocalDateTime.now().minusHours(8)))
        create(GabbleDto(text = "Gabble 1 from user matt_damon", createdById = "00ugl9afjiwNub6yt0h9", createdByUsername = "matt_damon", createdOn = LocalDateTime.now().minusHours(7)))
        create(GabbleDto(text = "Gabble 2 from user matt_damon", createdById = "00ugl9afjiwNub6yt0h9", createdByUsername = "matt_damon", createdOn = LocalDateTime.now().minusHours(18)))
        create(GabbleDto(text = "Gabble 1 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdByUsername = "frank_coenen", createdOn = LocalDateTime.now().minusHours(19)))
        create(GabbleDto(text = "Gabble 2 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdByUsername = "frank_coenen", createdOn = LocalDateTime.now().minusHours(21)))
        create(GabbleDto(text = "Gabble 3 from user frank_coenen", createdById = "00ugl9afjiwNub6yth10", createdByUsername = "frank_coenen", createdOn = LocalDateTime.now().minusHours(35)))
        create(GabbleDto(text = "Gabble 1 from user matthew_murdock", createdById = "00ugl9afjiwNub6yth11", createdByUsername = "matthew_murdock", createdOn = LocalDateTime.now().minusHours(41)))
    }

    override fun findByUserId(userId: String): Set<GabbleDto> {
        return this.gabbles.filter { dto -> dto.createdById == userId }.toSet()
    }

    override fun create(gabbleDto: GabbleDto): GabbleDto {
        val toSave = if (gabbleDto.createdOn == null)
            gabbleDto
                .copy(id = randomUUID(), createdOn = LocalDateTime.now())
                .extractTags()
        else
            gabbleDto
                .copy(id = randomUUID())
                .extractTags()

        this.gabbles.add(toSave)

        return toSave
    }

    override fun findAllGabbleTags(): Set<String> = this.gabbles.flatMap { gabble -> gabble.tags }.toSet()

    private fun GabbleDto.extractTags(): GabbleDto {
        val tags = this.text.split(" ")
            .filter { it -> it.startsWith("#") }
            .toMutableSet()

        return this.copy(tags = tags)
    }
}
