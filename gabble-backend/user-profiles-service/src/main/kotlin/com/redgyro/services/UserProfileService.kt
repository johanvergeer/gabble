package com.redgyro.services

import com.redgyro.dto.userprofiles.UserProfileDto
import com.redgyro.events.UserProfileUpdateEventPublisher
import com.redgyro.models.UserProfile
import com.redgyro.models.UserProfileHateosDto
import com.redgyro.repositories.UserProfileRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class UserProfileNotFoundException(userId: String) : Exception("User profile for user with id $userId not found")

@ResponseStatus(value = HttpStatus.CONFLICT)
class UserIdExistsException(userId: String) : Exception("User profile for user with id $userId already exists")

@Service
class UserProfileService(
    private val userProfileRepository: UserProfileRepository,
    private val userProfileUpdateEventPublisher: UserProfileUpdateEventPublisher) {

    fun findAllUserProfiles() = userProfileRepository.findAll().toList()

    fun findUserById(userId: String) = userProfileRepository
        .findById(userId)
        .orElseThrow { UserProfileNotFoundException(userId) }
        .toDto()

    fun findUserByUsername(username: String) = userProfileRepository
        .findByUsername(username)
        .orElseThrow { UserProfileNotFoundException(username) }
        .toDto()

    fun createNewUserProfile(userProfile: UserProfile): UserProfile {
        if (userProfileRepository.findById(userProfile.userId).isPresent) throw UserIdExistsException(userProfile.userId)

        return userProfileRepository.save(userProfile)
    }

    fun findNotFollowing(userId: String) = userProfileRepository
        .findNotFollowing(userProfileRepository.findById(userId).get())
        .map { it.toDto() }
        .toSet()

    fun followUser(followerId: String, followingId: String): Set<UserProfileHateosDto> {
        val follower = userProfileRepository.findById(followerId).orElseThrow { UserProfileNotFoundException(followerId) }
        val following = userProfileRepository.findById(followingId).orElseThrow { UserProfileNotFoundException(followingId) }

        follower.following.add(following)
        following.followers.add(follower)

        userProfileRepository.save(follower)

        return findNotFollowing(followingId)
    }

    fun saveUserProfile(userProfile: UserProfile): UserProfileHateosDto {
        // Find current user profile so we also have all relations
        val currentProfile = this.userProfileRepository
            .findById(userProfile.userId)
            .orElseThrow { UserProfileNotFoundException(userProfile.userId) }

        val profileToSave = currentProfile
            .copy(
                username = userProfile.username,
                bio = userProfile.bio,
                location = userProfile.location,
                website = userProfile.website
            ).apply {
                following.addAll(currentProfile.following)
                followers.addAll(currentProfile.followers)
            }

        val savedProfileDto = this.userProfileRepository.save(profileToSave).toDto()

        userProfileUpdateEventPublisher.publish(savedProfileDto)
        return savedProfileDto
    }

    fun findUserFollowing(userId: String) = this.userProfileRepository
        .findById(userId)
        .orElseThrow { UserProfileNotFoundException(userId) }
        .following
        .map { it.toDto() }

    fun findUserFollowers(userId: String) = this.userProfileRepository
        .findById(userId)
        .orElseThrow { UserProfileNotFoundException(userId) }
        .followers
        .map { it.toDto() }
}