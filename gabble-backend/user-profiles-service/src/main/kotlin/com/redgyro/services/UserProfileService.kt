package com.redgyro.services

import com.redgyro.dto.userprofiles.UserProfileDto
import com.redgyro.models.UserProfile
import com.redgyro.repositories.UserProfileRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class UserProfileNotFoundException(userId: String) : Exception("User profile for user with id $userId not found")

@ResponseStatus(value = HttpStatus.CONFLICT)
class UserIdExistsException(userId: String) : Exception("User profile for user with id $userId already exists")

@Service
class UserProfileService(private val userProfileRepository: UserProfileRepository) {

    fun findAllUserProfiles() = userProfileRepository.findAll().toList()

    fun findUserById(userId: String): UserProfileDto = userProfileRepository
        .findById(userId)
        .map {
            UserProfileDto(
                it.userId,
                it.username,
                it.bio,
                it.location,
                it.website,
                followersCount = it.followers.count(),
                followingCount = it.following.count())
        }
        .orElseThrow { UserProfileNotFoundException(userId) }

    fun createNewUserProfile(userProfile: UserProfile): UserProfile {
        if (userProfileRepository.findById(userProfile.userId).isPresent) throw UserIdExistsException(userProfile.userId)

        return userProfileRepository.save(userProfile)
    }
}