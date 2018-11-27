package com.redgyro.gabblesservice

import org.apache.activemq.ActiveMQConnectionFactory
import javax.inject.Inject
import javax.jms.Connection
import javax.jms.ConnectionFactory
import javax.jms.Session


class GabbleJmsConnectionFactory : ConnectionFactory {
    private val connectionFactory = ActiveMQConnectionFactory("tcp://localhost:61616")

    override fun createConnection(userName: String?, password: String?): Connection =
        connectionFactory.createConnection(userName, password)

    override fun createConnection(): Connection = connectionFactory.createConnection()
}

class UserProfileUpdateJmsSubscriber @Inject constructor(connectionFactory: ConnectionFactory) {
    init {
        val connection: Connection = connectionFactory.createConnection()

        try {
            connection.clientID = "DurabilityTest"
            val session: Session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
            val userProfileUpdateTopic = session.createTopic("userProfileUpdateTopic")

            val consumer1 = session.createDurableSubscriber(userProfileUpdateTopic, "userProfileUpdatesConsumer", "", false)

//            UserProfileUpdateEvent on { println("com.redgyro.gabblesservice.UserProfileUpdateEvent called with ${it.message}") }

            connection.start()

            while (true) {
                val consumer1Msg = consumer1.receive()

                println(consumer1Msg)

//                UserProfileUpdateEvent(consumer1Msg).emit()
            }

            session.close()
        } finally {
            connection.close()
        }
    }
}

fun main(args: Array<String>) {
    UserProfileUpdateJmsSubscriber(GabbleJmsConnectionFactory())
}