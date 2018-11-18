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

    fun findNotFollowing(userId: String): Set<UserProfileDto> = userProfileRepository
        .findNotFollowing(userProfileRepository.findById(userId).get())
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
        .toSet()

    fun followUser(followerId: String, followingId: String): Set<UserProfileDto> {
        val follower = userProfileRepository.findById(followerId).orElseThrow { UserProfileNotFoundException(followerId) }
        val following = userProfileRepository.findById(followingId).orElseThrow { UserProfileNotFoundException(followingId) }

        follower.following.add(following)
        following.followers.add(follower)

        userProfileRepository.save(follower)

        return findNotFollowing(followingId)
    }

    fun saveUserProfile(userProfile: UserProfile) = this.userProfileRepository
        .save(userProfile)
        .toDto()

    fun findUserFollowing(userId: String): Collection<UserProfileDto> = this.userProfileRepository
        .findById(userId)
        .orElseThrow { UserProfileNotFoundException(userId) }
        .following
        .map { it.toDto() }

    fun findUserFollowers(userId: String): Collection<UserProfileDto> = this.userProfileRepository
        .findById(userId)
        .orElseThrow { UserProfileNotFoundException(userId) }
        .followers
        .map { it.toDto() }
}