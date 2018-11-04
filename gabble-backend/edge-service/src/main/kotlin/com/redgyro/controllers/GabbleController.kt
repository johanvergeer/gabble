package com.redgyro.controllers

import com.redgyro.clients.GabbleClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [GabbleController.BASE_URL])
class GabbleController(private val gabbleClient: GabbleClient) {

    companion object {
        const val BASE_URL = "/gabbles"
    }

    @GetMapping
    fun getAllGabbles() = gabbleClient.getAllGabbles()
}