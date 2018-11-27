package com.redgyro.jms

import com.redgyro.events.UserProfileUpdateEvent
import org.springframework.context.ApplicationListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component


@Component
class UserProfileUpdateJmsPublisher(private val jmsTemplate: JmsTemplate) : ApplicationListener<UserProfileUpdateEvent> {
    override fun onApplicationEvent(event: UserProfileUpdateEvent) {
        jmsTemplate.convertAndSend("userProfileUpdateTopic", event.userProfileDto)
    }
}

//@Component
//class UserProfileUpdateJmsSubscriber {
//
//    @JmsListener(destination = "UserProfileUpdateQueue")
//    fun receiveMessage(userProfileDto: UserProfileDto) {
//        println("Received <$userProfileDto>")
//    }
//}