package com.redgyro.gabblesservice.service

import com.redgyro.dto.gabbles.GabbleDto
import javax.print.attribute.standard.JobOriginatingUserName

interface GabbleService {
    fun findByUserId(userId: String): Set<GabbleDto>

    suspend fun create(gabbleDto: GabbleDto): GabbleDto

    fun findAllGabbleTags(): Set<String>

    fun findMentionedInForUser(userId: String): Set<GabbleDto>

    fun changeMentionedUsername(userId: String, newUserName: String)
}