package com.redgyro.controllers

import com.redgyro.auth.getUserId
import com.redgyro.models.UserProfile
import com.redgyro.services.UserProfileService
import javassist.tools.web.BadHttpRequest
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
    fun getProfileForLoggedInUser(principal: Principal) = userProfileService.findUserById(principal.getUserId())

    @GetMapping(value = ["/profile/not-following/"])
    fun getNotFollowing(principal: Principal) = userProfileService.findNotFollowing(principal.getUserId())

    @PostMapping(value = ["/profile/following/"])
    fun followUser(@RequestBody payload: Map<String, String>, principal: Principal) =
        userProfileService.followUser(principal.getUserId(), payload["userId"]!!)

    @PostMapping
    fun createNewUserProfile(@RequestBody userProfile: UserProfile) = userProfileService.createNewUserProfile(userProfile)
}