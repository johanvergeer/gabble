package com.redgyro.dto.gabbles

import java.time.LocalDateTime

data class GabbleDto(
    val id: String = "",
    val text: String = "",
    val createdById: String = "",
    val createdByUsername: String = "",
    val createdOn: LocalDateTime? = null,
    val tags: MutableSet<String> = mutableSetOf(),
    val likedBy: MutableSet<String> = mutableSetOf(),
    val mentions: MutableSet<String> = mutableSetOf()
)

data class GabbleCreateDto(
    val text: String,
    val createdById: String
)