package com.redgyro.gabblesservice.guice

import com.google.inject.AbstractModule
import com.redgyro.gabblesservice.jms.GabbleJmsConnectionFactory
import com.redgyro.gabblesservice.jms.UserProfileMessageListener
import com.redgyro.gabblesservice.routes.MainRoutes
import com.redgyro.gabblesservice.service.GabbleService
import com.redgyro.gabblesservice.service.InMemoryGabbleService
import com.redgyro.gabblesservice.service.UserProfileRestService
import com.redgyro.gabblesservice.service.UserProfileService
import io.ktor.application.Application
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        bind(MainRoutes::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)

        install(JmsModule.Companion.Builder()
            .withConnectionFactory(GabbleJmsConnectionFactory())
            .withMessageListener(UserProfileMessageListener())
            .withTopicName("userProfileUpdateTopic")
            .buildModule())

        bind(HttpClient::class.java).toInstance(HttpClient(Apache) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        })

        bind(UserProfileService::class.java).to(UserProfileRestService::class.java)
        bind(GabbleService::class.java).to(InMemoryGabbleService::class.java)
    }
}