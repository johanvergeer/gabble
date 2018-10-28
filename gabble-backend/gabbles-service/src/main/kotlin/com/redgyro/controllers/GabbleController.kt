package com.redgyro.controllers

import com.redgyro.models.Gabble
import com.redgyro.services.GabbleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = [GabbleController.BASE_URL])
class GabbleController(private val gabbleService: GabbleService) {

    companion object {
        const val BASE_URL = "/gabbles"
    }

    @GetMapping
    fun getAllGabbles(): List<Gabble> = gabbleService.findAllGabbles()

    @PostMapping(value = ["/{gabbleId}/likes/{userId}"])
    fun addLikeToGabble(@PathVariable gabbleId: String, @PathVariable userId: String) = gabbleService.addLikeToGabble(gabbleId, userId)
}