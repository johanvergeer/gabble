package com.redgyro.gabblesservice.guice

import com.google.inject.AbstractModule
import javax.jms.*

/**
 * @see [Google Code](https://code.google.com/archive/p/guice-jms/) and [Github](https://github.com/aqingsao/i1-guice-jms)
 */
class JmsModule(
    private val session: Session,
    private val connection: Connection,
    private val destination: Destination) : AbstractModule() {

    override fun configure() {
        bind(Session::class.java).toInstance(session)
        bind(Connection::class.java).toInstance(connection)
        bind(Destination::class.java).toInstance(destination)
    }

    companion object {
        class Builder {
            private lateinit var connectionFactory: ConnectionFactory
            private lateinit var messageListener: MessageListener
            private lateinit var topicName: String

            fun buildModule(): JmsModule {
                val connection = connectionFactory.createConnection()
                connection.clientID = "GabblesServiceClient"
                val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
                val destination = session.createTopic(this.topicName)

                val topic = session.createTopic(this.topicName)
                val consumer = session.createDurableSubscriber(topic, "userProfileConsumer", "", false)
                consumer.messageListener = this.messageListener
                connection.start()

                return JmsModule(session, connection, destination)
            }

            fun withTopicName(topicName: String): Builder {
                this.topicName = topicName
                return this
            }

            fun withMessageListener(messageListener: MessageListener): Builder {
                this.messageListener = messageListener
                return this
            }

            fun withConnectionFactory(connectionFactory: ConnectionFactory): Builder {
                this.connectionFactory = connectionFactory
                return this
            }
        }
    }
}