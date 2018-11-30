package com.redgyro

import com.redgyro.models.UserProfile
import com.redgyro.repositories.UserProfileRepository
import com.redgyro.services.UserProfileService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.stereotype.Component


@SpringBootApplication
class ProfilesServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
        .sources(ProfilesServiceApplication::class.java)
        .run(*args)
}


@Component
class StartUpRunner(private val userProfileService: UserProfileService, private val userProfileRepository: UserProfileRepository) : CommandLineRunner {
    override fun run(vararg args: String) {
        if (userProfileRepository.count() <= 0) {
            var user1 = userProfileService.createNewUserProfile(UserProfile(
                userId = "00ugl9afjiwNub6yt0h7",
                username = "johan_vergeer",
                bio = "Dit is de biografie van Johan Vergeer. Er is eigenlijk niet zo veel te vertellen, maar we proberen toch maar 160 tekens te halen. En dat is dus nu bijna nog ach",
                location = "Helmond",
                website = "https://github.com/johanvergeer"
            ))
            var user2 = userProfileService.createNewUserProfile(UserProfile(
                userId = "00ugl9afjiwNub6yt0h8",
                username = "floris_bosch",
                bio = "Dit is de biografie van Floris Bosch",
                location = "'s Hertogenbosch",
                website = "https://github.com/floris"
            ))
            var user3 = userProfileService.createNewUserProfile(UserProfile(
                userId = "00ugl9afjiwNub6yt0h9",
                username = "matt_damon",
                bio = "Dit is de biografie van Matt Damon",
                location = "United States",
                website = "https://youtube.com/mattd"
            ))
            var user4 = userProfileService.createNewUserProfile(UserProfile(
                userId = "00ugl9afjiwNub6yth10",
                username = "frank_coenen",
                bio = "Dit is de biografie van Frank C.",
                location = "Helmond",
                website = "https://facebook.com/fc"
            ))
            var user5 = userProfileService.createNewUserProfile(UserProfile(
                userId = "00ugl9afjiwNub6yth11",
                username = "matthew_murdock",
                bio = "Dit is de biografie van MM",
                location = "Hell's Kitchen, NY",
                website = "https://facebook.com/daredevil"
            ))

            user2.following.add(user1)
            user1.followers.add(user2)
            user2 = userProfileRepository.save(user2)

            user3.following.add(user1)
            user1.followers.add(user3)
            userProfileRepository.save(user3)

            user1.following.add(user4)
            user4.followers.add(user1)
            user1 = userProfileRepository.save(user1)

            user1.following.add(user2)
            user2.followers.add(user1)
            userProfileRepository.save(user1)
        }
    }
}
