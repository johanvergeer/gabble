package com.redgyro.controllers

import com.redgyro.models.Gabble
import com.redgyro.models.GabbleCreateDto
import com.redgyro.services.GabbleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = [GabbleController.BASE_URL])
class GabbleController(private val gabbleService: GabbleService) {

    companion object {
        const val BASE_URL = "/gabbles"
    }

    @PostMapping
    fun createNewGabble(@RequestBody gabbleCreateDto: GabbleCreateDto): Gabble =
            gabbleService.saveGabble(gabbleCreateDto.text, gabbleCreateDto.createdById)

    @GetMapping
    fun getAllGabbles(): List<Gabble> = gabbleService.findAllGabbles()

    @GetMapping(value = ["/{userId}/"])
    fun getAllGabblesForUser(@RequestParam userId: String): List<Gabble> = gabbleService.findByUserId(userId)

    @PostMapping(value = ["/{gabbleId}/likes/{userId}/"])
    fun addLikeToGabble(@PathVariable gabbleId: String, @PathVariable userId: String) {
        gabbleService.addLikeToGabble(gabbleId, userId)
    }
}