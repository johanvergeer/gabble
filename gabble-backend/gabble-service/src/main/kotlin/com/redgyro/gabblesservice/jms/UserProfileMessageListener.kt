package com.redgyro.gabblesservice.jms

import com.google.gson.Gson
import com.redgyro.dto.userprofiles.UserProfileDto
import com.redgyro.gabblesservice.events.UserProfileUpdateEvent
import org.apache.activemq.command.ActiveMQTextMessage
import javax.jms.Message
import javax.jms.MessageListener

class UserProfileMessageListener : MessageListener {
    override fun onMessage(message: Message?) {
        if (message is ActiveMQTextMessage) {
            println("Received message => ${message.text}")

            val gson = Gson()
            val userProfileDto = gson.fromJson(message.text, UserProfileDto::class.java)

            UserProfileUpdateEvent(userProfileDto).emit()
        }
    }
}