package guice

import com.google.inject.AbstractModule
import io.ktor.application.Application
import routes.MainRoutes
import service.GabbleService
import service.InMemoryGabbleService

class MainModule(private val application: Application): AbstractModule() {
    override fun configure() {
        bind(MainRoutes::class.java).asEagerSingleton()
        bind(Application::class.java).toInstance(application)
        bind(GabbleService::class.java).to(InMemoryGabbleService::class.java)
    }
}