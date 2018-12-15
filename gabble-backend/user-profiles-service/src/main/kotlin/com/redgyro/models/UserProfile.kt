package com.redgyro.models

import com.redgyro.dto.userprofiles.UserProfileDto
import org.hibernate.validator.constraints.URL
import org.springframework.hateoas.ResourceSupport
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
data class UserProfile(
    @Id
    var userId: String = "",
    var username: String = "",
    @Size(max = 160)
    val bio: String = "",
    val location: String = "",
    @URL
    val website: String = ""
) {
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "following")
    val followers = mutableSetOf<UserProfile>()

    @JoinTable(name = "kwetter_user_followers",
        joinColumns = [JoinColumn(name = "follower_id", referencedColumnName = "userId", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "following_id", referencedColumnName = "userId", nullable = false)])
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.DETACH, CascadeType.REFRESH])
    val following = mutableSetOf<UserProfile>()

    fun toDto() = UserProfileHateosDto(
        this.userId,
        this.username,
        this.bio,
        this.location,
        this.website,
        this.followers.count(),
        this.following.count()
    )
}


open class UserProfileHateosDto(
    var userId: String = "",
    var username: String = "",
    val bio: String = "",
    val location: String = "",
    val website: String = "",
    val followersCount: Int = 0,
    val followingCount: Int = 0
) : ResourceSupport()

