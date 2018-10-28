package com.redgyro.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import java.time.LocalDateTime

@TypeAlias("gabble")
data class Gabble(
        @Id val id: String = "",
        val text: String = "",
        val createdById: Long = 0,
        val createdOn: LocalDateTime? = null,
        val tags: Set<String> = setOf(),
        val likedBy: Set<Long> = setOf(),
        val mentions: Set<Long> = setOf()
)