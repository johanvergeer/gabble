package com.redgyro.controllers

import com.redgyro.services.UserProfileService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = [UserProfilesController.BASE_URL])
class UserProfilesController(private val userProfileService: UserProfileService) {
    companion object {
        const val BASE_URL = "/user-profiles"
    }

    @GetMapping
    fun getAllUserProfiles() = userProfileService.findAllUserProfiles()

    @GetMapping(value = ["/{userId}/"])
    fun getUserProfile(@PathVariable userId: String) = userProfileService.findUserById(userId)
}