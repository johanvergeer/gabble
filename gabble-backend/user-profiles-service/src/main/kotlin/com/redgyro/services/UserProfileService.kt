package com.redgyro.services

import com.redgyro.repositories.UserProfileRepository
import org.springframework.stereotype.Service

@Service
class UserProfileService(private val userProfileRepository: UserProfileRepository) {

    fun findAllUserProfiles() = userProfileRepository.findAll().toList()

}