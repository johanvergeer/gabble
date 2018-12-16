package com.redgyro.gabblesservice.events

import com.redgyro.dto.gabbles.GabbleDto

class GabbleCreateEvent(val gabbleDto: GabbleDto) {
    companion object : Event<GabbleCreateEvent>()

    fun emit() = emit(this)
}