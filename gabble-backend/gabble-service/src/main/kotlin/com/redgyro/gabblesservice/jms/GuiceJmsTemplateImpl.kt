package com.redgyro.gabblesservice.jms

import com.google.inject.Inject
import org.springframework.jms.core.MessageCreator
import javax.jms.Connection
import javax.jms.Destination
import javax.jms.Message
import javax.jms.Session

class GuiceJmsTemplateImpl @Inject constructor(
    private val session: Session,
    private val destination: Destination,
    private val connection: Connection) : GuiceJmsTemplate {

    override fun send(messageCreator: MessageCreator) {
        val message = messageCreator.createMessage(session)
        session.createProducer(destination).send(message)
    }

    override fun receive(): Message {
        val consumer = session.createConsumer(destination)
        connection.start()
        return consumer.receive()
    }

    override fun close() {
        connection.close()
        session.close()
    }
}