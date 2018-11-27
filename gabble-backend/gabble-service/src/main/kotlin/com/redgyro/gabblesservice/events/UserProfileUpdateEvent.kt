package com.redgyro.gabblesservice.events

import com.redgyro.dto.userprofiles.UserProfileDto

class UserProfileUpdateEvent(val userProfileDto: UserProfileDto) {
    companion object : Event<UserProfileUpdateEvent>()

    fun emit() = emit(this)
}