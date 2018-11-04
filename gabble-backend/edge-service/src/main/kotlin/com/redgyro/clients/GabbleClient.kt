package com.redgyro.clients

import com.redgyro.dto.gabbles.GabbleCreateDto
import com.redgyro.dto.gabbles.GabbleDto
import org.springframework.cloud.netflix.ribbon.RibbonClient
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Component
@FeignClient(value = "gabbles-service")
@RibbonClient(value = "gabbles-service")
interface GabbleClient {

    @GetMapping(value = ["/gabbles"])
    fun getAllGabbles(): List<GabbleDto>

    @PostMapping
    fun createNewGabble(@RequestBody gabbleCreateDto: GabbleCreateDto): GabbleDto

    @PostMapping(value = ["/gabbles/{gabbleId}/likes/{userId}"])
    fun addLikeToGabble(@PathVariable gabbleId: String, @PathVariable userId: String)
}