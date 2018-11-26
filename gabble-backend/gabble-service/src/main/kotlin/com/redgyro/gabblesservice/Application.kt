package com.redgyro.gabblesservice

import com.google.inject.Guice
import com.redgyro.gabblesservice.config.LocalDateTimeConverter
import com.redgyro.gabblesservice.exception.AuthenticationException
import com.redgyro.gabblesservice.exception.AuthorizationException
import com.redgyro.gabblesservice.guice.JmsModule
import com.redgyro.gabblesservice.guice.MainModule
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
import io.ktor.response.respond
import java.time.LocalDateTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.DevelopmentEngine.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    Guice.createInjector(MainModule(this), JmsModule())

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

    install(StatusPages) {
        exception<AuthenticationException> { call.respond(HttpStatusCode.Unauthorized) }
        exception<AuthorizationException> { call.respond(HttpStatusCode.Forbidden) }
    }
}




