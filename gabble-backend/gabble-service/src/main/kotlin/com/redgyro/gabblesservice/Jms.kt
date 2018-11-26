package com.redgyro.gabblesservice

import com.redgyro.gabblesservice.events.Event
import org.apache.activemq.ActiveMQConnectionFactory
import javax.inject.Inject
import javax.jms.*

class UserProfileUpdateEvent(val message: String) {
    companion object : Event<UserProfileUpdateEvent>()

    fun emit() = emit(this)
}

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
            // Producer
            connection.clientID = "DurabilityTest"
            val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
            val topic = session.createTopic("customerTopic")

            // Publish
            val payload = "Important Task"
            val msg = session.createTextMessage(payload)
            val publisher = session.createProducer(topic)
            println("sending text '$payload'")
            publisher.send(
                msg,
                javax.jms.DeliveryMode.PERSISTENT,
                javax.jms.Message.DEFAULT_PRIORITY,
                Message.DEFAULT_TIME_TO_LIVE)

            val consumer1 = session.createDurableSubscriber(topic, "consumer1", "", false)
            val consumer2 = session.createDurableSubscriber(topic, "consumer2", "", false)

            UserProfileUpdateEvent on { println("com.redgyro.gabblesservice.UserProfileUpdateEvent called with ${it.message}") }

            connection.start()

            while (true) {
                val consumer1Msg = consumer1.receive() as TextMessage
                println("Consumer 1 receives '${consumer1Msg.text}'")

                val consumer2Msg = consumer2.receive() as TextMessage
                UserProfileUpdateEvent(consumer2Msg.text).emit()
                println("Consumer 2 receives '${consumer2Msg.text}'")
            }

            session.close()
        } finally {
            connection.close()
        }
    }
}