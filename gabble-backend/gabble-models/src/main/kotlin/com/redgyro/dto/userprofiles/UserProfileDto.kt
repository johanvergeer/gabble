package com.redgyro.dto.userprofiles

data class UserProfileDto(
        var userId: String = "",
        val bio: String = "",
        val location: String = "",
        val website: String = ""
)
