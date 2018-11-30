package com.redgyro.gabblesservice

import com.redgyro.gabblesservice.jms.GabbleJmsConnectionFactory
import com.redgyro.gabblesservice.jms.UserProfileMessageListener
import org.apache.activemq.ActiveMQConnectionFactory
import javax.inject.Inject
import javax.jms.Connection
import javax.jms.ConnectionFactory
import javax.jms.Session

class UserProfileUpdateJmsSubscriber @Inject constructor(connectionFactory: ConnectionFactory) {
    init {
        val connection: Connection = connectionFactory.createConnection()

        try {
            connection.clientID = "DurabilityTest"

            val session: Session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)

            val userProfileUpdateTopic = session.createTopic("userProfileUpdateTopic")

            val consumer1 = session.createDurableSubscriber(userProfileUpdateTopic, "userProfileUpdatesConsumer", "", false)
            consumer1.messageListener = UserProfileMessageListener()

//            UserProfileUpdateEvent on { println("com.redgyro.gabblesservice.UserProfileUpdateEvent called with ${it.message}") }

            connection.start()

            Thread.sleep(10000000)

//            while (true) {
//                val consumer1Msg = consumer1.receive()
//
//                println(consumer1Msg)
//
////                UserProfileUpdateEvent(consumer1Msg).emit()
//            }

            session.close()
        } finally {
            connection.close()
        }
    }
}

fun main(args: Array<String>) {
    UserProfileUpdateJmsSubscriber(GabbleJmsConnectionFactory())
}