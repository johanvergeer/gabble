package com.redgyro.controllers

import com.redgyro.services.UserProfileService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [UserProfilesController.BASE_URL])
class UserProfilesController(private val userProfileService: UserProfileService) {
    companion object {
        const val BASE_URL = "user-profiles"
    }

    @GetMapping
    fun getAllUserProfiles() = userProfileService.findAllUserProfiles()
}