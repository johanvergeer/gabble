package com.redgyro.services

import com.redgyro.models.Gabble
import com.redgyro.repositories.GabbleRepository
import org.springframework.stereotype.Service

class GabbleNotFoundException(gabbleId: String) : Exception("Gabble with id $gabbleId not found")

@Service
class GabbleService(private val gabbleRepository: GabbleRepository) {

    fun findAllGabbles() = gabbleRepository.findAll().toList()

    fun saveGabble(gabble: Gabble) = gabbleRepository.save(gabble)

    fun addLikeToGabble(gabbleId: String, userId: String) {
        val gabble = gabbleRepository
                .findById(gabbleId)
                .orElseThrow { GabbleNotFoundException(gabbleId) }

        gabble.likedBy.add(userId)
        gabbleRepository.save(gabble)
    }
}