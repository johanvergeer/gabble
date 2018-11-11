package com.redgyro.dto.userprofiles

data class UserProfileDto(
    var userId: String = "",
    var username: String = "",
    val bio: String = "",
    val location: String = "",
    val website: String = "",
    val followersCount: Int = 0,
    val followingCount: Int = 0
)
