package com.redgyro

import com.redgyro.models.Gabble
import com.redgyro.repositories.GabbleRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.time.LocalDateTime
import java.util.*


@SpringBootApplication
@EnableDiscoveryClient
class GabblesServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
        .sources(GabblesServiceApplication::class.java)
        .run(*args)
}

fun randomUUIDAsString() = UUID.randomUUID().toString()

@Component
@Profile("dev")
class StartupCommandLineRunner(private val gabbleRepository: GabbleRepository) : CommandLineRunner {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(StartupCommandLineRunner::class.java)

        val GABBLE_1 = Gabble(id = randomUUIDAsString(), text = "Gabble 1 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
        val GABBLE_2 = Gabble(id = randomUUIDAsString(), text = "Gabble 2 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
        val GABBLE_3 = Gabble(id = randomUUIDAsString(), text = "Gabble 3 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
        val GABBLE_4 = Gabble(id = randomUUIDAsString(), text = "Gabble 4 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
        val GABBLE_5 = Gabble(id = randomUUIDAsString(), text = "Gabble 5 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
    }

    override fun run(vararg args: String?) {
        if (gabbleRepository.count() <= 0) {
            logger.info("No gabbles in database. Loading dummy data.")
            gabbleRepository.save(GABBLE_1)
            gabbleRepository.save(GABBLE_2)
            gabbleRepository.save(GABBLE_3)
            gabbleRepository.save(GABBLE_4)
            gabbleRepository.save(GABBLE_5)
            logger.info("Dummy data loaded.")
        } else {
            logger.info("Database already contains Gabble objects")
        }
    }
}

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