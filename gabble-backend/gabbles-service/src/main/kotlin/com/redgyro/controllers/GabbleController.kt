package com.redgyro.controllers

import com.redgyro.models.Gabble
import com.redgyro.repositories.GabbleRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [GabbleController.BASE_URL])
class GabbleController(private val gabbleRepository: GabbleRepository) {

    companion object {
        const val BASE_URL = "/gabbles"
    }

    @GetMapping
    fun getAllGabbles(): List<Gabble> = gabbleRepository.findAll().toList()
}