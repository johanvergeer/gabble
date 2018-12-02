package com.redgyro.gabblesservice.service

import com.google.inject.Inject
import com.redgyro.dto.userprofiles.UserProfileDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

interface UserProfileService {
    suspend fun findById(userId: String): UserProfileDto

    suspend fun findByUsername(username: String): UserProfileDto
}