package com.redgyro.gabblesservice.jms

import org.springframework.jms.JmsException
import org.springframework.jms.core.MessageCreator
import javax.jms.JMSException
import javax.jms.Message

interface GuiceJmsTemplate: AutoCloseable {
    @Throws(JMSException::class)
    fun send(messageCreator: MessageCreator)

    @Throws(JmsException::class)
    fun receive(): Message
}