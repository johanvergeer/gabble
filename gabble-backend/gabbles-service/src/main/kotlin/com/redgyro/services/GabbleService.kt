package com.redgyro.services

import com.redgyro.models.Gabble
import com.redgyro.repositories.GabbleRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.NOT_FOUND)
class GabbleNotFoundException(gabbleId: String) : Exception("Gabble with id $gabbleId not found")

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
class CannotLikeOwnGabbleException() : Exception("User cannot like own Gabble")

@Service
class GabbleService(private val gabbleRepository: GabbleRepository) {

    fun findAllGabbles() = gabbleRepository.findAll().toList()

    fun saveGabble(gabble: Gabble) = gabbleRepository.save(gabble)

    fun addLikeToGabble(gabbleId: String, userId: String) {
        val gabble = gabbleRepository
                .findById(gabbleId)
                .orElseThrow { GabbleNotFoundException(gabbleId) }

        if (gabble.createdById == userId) throw CannotLikeOwnGabbleException()

        gabble.likedBy.add(userId)
        gabbleRepository.save(gabble)
    }
}