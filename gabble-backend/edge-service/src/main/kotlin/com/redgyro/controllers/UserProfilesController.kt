package com.redgyro.controllers

import com.redgyro.clients.UserProfileClient
import com.redgyro.dto.userprofiles.UserProfileDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [UserProfilesController.BASE_URL])
class UserProfilesController(private val userProfileClient: UserProfileClient) {

    companion object {
        const val BASE_URL = "/user-profiles"
    }

    @GetMapping
    fun getAllUserProfiles(): List<UserProfileDto> = userProfileClient.getAllUserProfiles()
}