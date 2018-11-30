package com.redgyro.gabblesservice.guice

import com.google.inject.AbstractModule
import com.redgyro.gabblesservice.UserProfileUpdateJmsSubscriber
import com.redgyro.gabblesservice.jms.GabbleJmsConnectionFactory
import javax.jms.ConnectionFactory

class JmsModule : AbstractModule() {
    override fun configure() {
        bind(ConnectionFactory::class.java).toInstance(GabbleJmsConnectionFactory())
        bind(UserProfileUpdateJmsSubscriber::class.java).asEagerSingleton()
    }
}