import events.Event
import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Message
import javax.jms.Session
import javax.jms.TextMessage

class UserProfileUpdateEvent(val message: String) {
    companion object : Event<UserProfileUpdateEvent>()

    fun emit() = emit(this)
}

class UserProfileUpdateJmsSubscriber {
    init {
        val connectionFactory = ActiveMQConnectionFactory("tcp://localhost:61616")
        val connection = connectionFactory.createConnection()

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

            UserProfileUpdateEvent on { println("UserProfileUpdateEvent called with ${it.message}") }

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

fun main(args: Array<String>) {
    val userProfileUpdateJmsSubscriber = UserProfileUpdateJmsSubscriber()
}