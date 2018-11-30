package com.redgyro.gabblesservice.jms

import org.apache.activemq.command.ActiveMQTextMessage
import javax.jms.Message
import javax.jms.MessageListener

class UserProfileMessageListener : MessageListener {
    override fun onMessage(message: Message?) {
        val textMessage = message as ActiveMQTextMessage
        println("===========================================================")
        println(message.text)
        println("===========================================================")
    }
}