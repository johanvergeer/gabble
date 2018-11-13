package com.redgyro.repositories

import com.redgyro.dto.userprofiles.UserProfileDto
import com.redgyro.models.UserProfile
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProfileRepository : CrudRepository<UserProfile, String> {

//    @Query("select u1, u2 from  u1 UserProfile, u2 UserPofile where u.userId not in (select )")
//    fun findNotFollowing(userId: String): Set<UserProfile>
}