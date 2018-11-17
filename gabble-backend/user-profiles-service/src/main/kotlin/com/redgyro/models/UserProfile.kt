package com.redgyro.models

import com.redgyro.dto.userprofiles.UserProfileDto
import org.hibernate.validator.constraints.URL
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
//@NamedQuery(name = "UserProfile.findNotFollowing",
//    query = "select u from UserProfile u where :userProfile not member of u.followers")
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

    fun toDto()= UserProfileDto(
            this.userId,
            this.username,
            this.bio,
            this.location,
            this.website,
            this.followers.count(),
            this.following.count()
        )
}

