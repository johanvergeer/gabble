package com.redgyro.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import java.time.LocalDateTime

@TypeAlias("gabble")
data class Gabble(
        @Id val id: String = "",
        val text: String = "",
        val createdById: String = "",
        val createdOn: LocalDateTime? = null,
        val tags: MutableSet<String> = mutableSetOf(),
        val likedBy: MutableSet<String> = mutableSetOf(),
        val mentions: MutableSet<Long> = mutableSetOf()
)

data class GabbleCreateDto(
        val text: String,
        val createdById: String
)