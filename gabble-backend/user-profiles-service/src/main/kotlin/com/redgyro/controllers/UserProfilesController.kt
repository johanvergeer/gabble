package com.redgyro.controllers

import com.redgyro.models.UserProfile
import com.redgyro.services.UserProfileService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping(value = [UserProfilesController.BASE_URL])
class UserProfilesController(private val userProfileService: UserProfileService) {
    companion object {
        const val BASE_URL = "/user-profiles"
    }

    @GetMapping
    fun getAllUserProfiles(): List<UserProfile> = userProfileService.findAllUserProfiles()

    @GetMapping(value = ["/{userId}/"])
    fun getUserProfile(@PathVariable userId: String) = userProfileService.findUserById(userId)

    @GetMapping(value = ["/profile/"])
    fun getUserProfile(principal: Principal) {
//        val profile = userProfileService.findUserById()

        println(principal)
    }

    @PostMapping
    fun createNewUserProfile(@RequestBody userProfile: UserProfile) = userProfileService.createNewUserProfile(userProfile)
}