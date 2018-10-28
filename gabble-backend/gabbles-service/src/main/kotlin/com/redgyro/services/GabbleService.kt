package com.redgyro.services

import com.redgyro.models.Gabble
import com.redgyro.repositories.GabbleRepository
import org.springframework.stereotype.Service

@Service
class GabbleService(private val gabbleRepository: GabbleRepository) {

    fun findAllGabbles() = gabbleRepository.findAll()

    fun saveGabble(gabble: Gabble) = gabbleRepository.save(gabble)
}