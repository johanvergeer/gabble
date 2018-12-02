package com.redgyro.gabblesservice.service

import com.google.inject.Inject
import com.redgyro.dto.userprofiles.UserProfileDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get


class UserProfileRestService @Inject constructor(private val client: HttpClient) : UserProfileService {

    companion object {
        private const val BASE_URL = "http://localhost:8090/user-profiles"
    }

    override suspend fun findById(userId: String) =
        client.get<UserProfileDto>("$BASE_URL/$userId/")

    override suspend fun findByUsername(username: String) =
        client.get<UserProfileDto>("$BASE_URL/?username=$username")
}