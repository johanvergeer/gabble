package com.redgyro.models

import org.hibernate.validator.constraints.URL
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
data class UserProfile (
    @Id
    var userId: Long = 0,
    @Size(max = 160)
    val bio: String = "",
    val location: String = "",
    @URL
    val website: String = ""
)