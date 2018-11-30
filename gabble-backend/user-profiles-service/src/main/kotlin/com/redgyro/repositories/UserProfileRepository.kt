package com.redgyro.repositories

import com.redgyro.models.UserProfile
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository : CrudRepository<UserProfile, String> {

    @Query("select u from UserProfile u where :userProfile not member of u.followers and u <> :userProfile")
    fun findNotFollowing(userProfile: UserProfile): Set<UserProfile>
}