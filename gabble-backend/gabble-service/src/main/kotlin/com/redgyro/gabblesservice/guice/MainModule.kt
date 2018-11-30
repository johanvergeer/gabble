package com.redgyro.gabblesservice.guice

import com.google.inject.AbstractModule
import com.redgyro.gabblesservice.jms.GabbleJmsConnectionFactory
import com.redgyro.gabblesservice.jms.UserProfileMessageListener
import com.redgyro.gabblesservice.routes.MainRoutes
import com.redgyro.gabblesservice.service.GabbleService
import com.redgyro.gabblesservice.service.InMemoryGabbleService
import io.ktor.application.Application

class MainModule(private val application: Application) : AbstractModule() {
    override fun configure() {
        bind(MainRoutes::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
        bind(GabbleService::class.java).to(InMemoryGabbleService::class.java)

        install(JmsModule.Companion.Builder()
            .withConnectionFactory(GabbleJmsConnectionFactory())
            .withMessageListener(UserProfileMessageListener())
            .withTopicName("userProfileUpdateTopic")
            .buildModule())
    }
}