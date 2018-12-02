package com.redgyro.gabblesservice.service

import com.redgyro.dto.gabbles.GabbleDto

interface GabbleService {
    fun findByUserId(userId: String): Set<GabbleDto>

    suspend fun create(gabbleDto: GabbleDto): GabbleDto

    fun findAllGabbleTags(): Set<String>
}