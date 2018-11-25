package service

import com.redgyro.dto.gabbles.GabbleDto

interface GabbleService {
    fun findByUserId(userId: String): Set<GabbleDto>

    fun create(gabbleDto: GabbleDto): GabbleDto

    fun findAllGabbleTags(): Set<String>
}