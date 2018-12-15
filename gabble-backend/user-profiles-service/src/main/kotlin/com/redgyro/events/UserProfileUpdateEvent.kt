package com.redgyro.events

import com.redgyro.dto.userprofiles.UserProfileDto
import com.redgyro.models.UserProfileHateosDto
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

class UserProfileUpdateEvent(source: Any, val userProfileDto: UserProfileHateosDto) : ApplicationEvent(source)

@Component
class UserProfileUpdateEventPublisher(private val applicationEventPublisher: ApplicationEventPublisher) {
    fun publish(userProfileDto: UserProfileHateosDto) {
        applicationEventPublisher.publishEvent(UserProfileUpdateEvent(this, userProfileDto))
    }
}