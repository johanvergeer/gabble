package com.redgyro.models

import com.redgyro.dto.userprofiles.UserProfileDto
import org.hibernate.validator.constraints.URL
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
    @ManyToMany(mappedBy = "following")
    val followers = mutableSetOf<UserProfile>()

    @JoinTable(name = "kwetter_user_followers",
        joinColumns = [JoinColumn(name = "follower_id", referencedColumnName = "userId", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "followed_id", referencedColumnName = "userId", nullable = false)])
    @ManyToMany(cascade = [CascadeType.ALL])
    val following = mutableSetOf<UserProfile>()
}
