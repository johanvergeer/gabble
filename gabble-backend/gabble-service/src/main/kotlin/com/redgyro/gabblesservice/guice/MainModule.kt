package com.redgyro.gabblesservice.guice

import com.google.inject.AbstractModule
import io.ktor.application.Application
import com.redgyro.gabblesservice.routes.MainRoutes
import com.redgyro.gabblesservice.service.GabbleService
import com.redgyro.gabblesservice.service.InMemoryGabbleService

class MainModule(private val application: Application): AbstractModule() {
    override fun configure() {
        bind(MainRoutes::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
        bind(GabbleService::class.java).to(InMemoryGabbleService::class.java)
    }
}