package com.redgyro

import com.redgyro.models.UserProfile
import com.redgyro.repositories.UserProfileRepository
import com.redgyro.services.UserProfileService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.util.*


@SpringBootApplication
@EnableDiscoveryClient
class ProfilesServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
        .sources(ProfilesServiceApplication::class.java)
        .run(*args)
}

fun randomUUIDAsString() = UUID.randomUUID().toString()

@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.requestMatcher(RequestHeaderRequestMatcher("Authorization"))
            .authorizeRequests()
            .anyRequest()
            .fullyAuthenticated()
    }
}

@Configuration
class RestConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().apply {
            allowCredentials = true
            addAllowedOrigin("*")
            addAllowedHeader("*")
            addAllowedMethod("OPTIONS")
            addAllowedMethod("GET")
            addAllowedMethod("POST")
            addAllowedMethod("PUT")
            addAllowedMethod("DELETE")
        }

        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}

@Component
class StartUpRunner(private val userProfileService: UserProfileService, private val userProfileRepository: UserProfileRepository) : CommandLineRunner {
    override fun run(vararg args: String) {
        if (userProfileRepository.count() <= 0) {
            val user_1 = userProfileService.createNewUserProfile(UserProfile(
                userId = "00ugl9afjiwNub6yt0h7",
                username = "johan_vergeer",
                bio = "Dit is de biografie van Johan Vergeer. Er is eigenlijk niet zo veel te vertellen, maar we proberen toch maar 160 tekens te halen. En dat is dus nu bijna nog ach",
                location = "Helmond",
                website = "https://github.com/johanvergeer"
            ))
            val user_2 = userProfileService.createNewUserProfile(UserProfile(
                userId = "00ugl9afjiwNub6yt0h8",
                username = "floris_bosch",
                bio = "Dit is de biografie van Floris Bosch",
                location = "'s Hertogenbosch",
                website = "https://github.com/floris"
            ))

            user_2.following.add(user_1)
            user_1.followers.add(user_2)

            userProfileRepository.save(user_2)

//            println(user_2)
//
//            user_1.following.add(user_2)
//
//            val saved_user_1 = userProfileRepository.findById("00ugl9afjiwNub6yt0h7")
//            println(saved_user_1)

//            userProfileRepository.save(user_1)
        }
    }
}

val profiles = listOf(
    UserProfile(
        userId = "00ugl9afjiwNub6yt0h9",
        username = "matt_damon",
        bio = "Dit is de biografie van Matt Damon",
        location = "United States",
        website = "https://youtube.com/mattd"
    ),
    UserProfile(
        userId = "00ugl9afjiwNub6yth10",
        username = "frank_coenen",
        bio = "Dit is de biografie van Frank C.",
        location = "Helmond",
        website = "https://facebook.com/fc"
    ),
    UserProfile(
        userId = "00ugl9afjiwNub6yth11",
        username = "matthew_murdock",
        bio = "Dit is de biografie van MM",
        location = "Hell's Kitchen, NY",
        website = "https://facebook.com/daredevil"
    )

)