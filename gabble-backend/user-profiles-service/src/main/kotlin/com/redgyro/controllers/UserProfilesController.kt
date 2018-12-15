package com.redgyro.controllers

import com.redgyro.auth.getUserId
import com.redgyro.dto.userprofiles.UserProfileDto
import com.redgyro.models.UserProfile
import com.redgyro.models.UserProfileHateosDto
import com.redgyro.services.UserProfileService
import org.springframework.hateoas.Link
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping(value = [UserProfilesController.BASE_URL])
open class UserProfilesController(private val userProfileService: UserProfileService) {
    companion object {
        const val BASE_URL = "/user-profiles"
    }

    @GetMapping
    fun getAllUserProfiles(): List<UserProfile> = userProfileService.findAllUserProfiles()

    @GetMapping(value = ["/{userId}/"])
    fun getUserProfile(@PathVariable userId: String) =
        userProfileService.findUserById(userId)

    @GetMapping(value = ["/"])
    fun getUserProfileByUsername(@RequestParam username: String) =
        userProfileService.findUserByUsername(username)

    @GetMapping(value = ["/{userId}/following/"])
    fun getUserFollowing(@PathVariable userId: String) =
        userProfileService.findUserFollowing(userId)

    @GetMapping(value = ["/{userId}/followers/"])
    fun getUserFollowers(@PathVariable userId: String) =
        userProfileService.findUserFollowers(userId)

    @GetMapping(value = ["/profile/"])
    fun getProfileForLoggedInUser(principal: Principal): Resource<UserProfileHateosDto> {
        val link = linkTo(methodOn(UserProfilesController::class.java)
            .getUserProfile(principal.getUserId()))
            .withSelfRel()

        val userProfile = userProfileService.findUserById(principal.getUserId())

        return Resource<UserProfileHateosDto>(userProfile, link)
    }

    @PutMapping(value = ["/profile/"])
    fun updateProfileForLoggedInUser(@RequestBody userProfile: UserProfile, principal: Principal) =
        userProfileService.saveUserProfile(userProfile)

    @GetMapping(value = ["/profile/not-following/"])
    fun getNotFollowing(principal: Principal) = userProfileService.findNotFollowing(principal.getUserId())

    @PostMapping(value = ["/profile/following/"])
    fun followUser(@RequestBody payload: Map<String, String>, principal: Principal) =
        userProfileService.followUser(principal.getUserId(), payload["userId"]!!)

    @PostMapping
    fun createNewUserProfile(@RequestBody userProfile: UserProfile) = userProfileService.createNewUserProfile(userProfile)
}