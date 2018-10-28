package com.redgyro

import com.redgyro.models.Gabble
import com.redgyro.repositories.GabbleRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.actuate.trace.http.HttpTrace
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer


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
class StartupCommandLineRunner(private val gabbleRepository: GabbleRepository) : CommandLineRunner {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(StartupCommandLineRunner::class.java)

        val GABBLE_1 = Gabble(id = randomUUIDAsString(), text = "Gabble 1 text", createdById = 1, createdOn = LocalDateTime.now())
        val GABBLE_2 = Gabble(id = randomUUIDAsString(), text = "Gabble 2 text", createdById = 1, createdOn = LocalDateTime.now())
        val GABBLE_3 = Gabble(id = randomUUIDAsString(), text = "Gabble 3 text", createdById = 1, createdOn = LocalDateTime.now())
        val GABBLE_4 = Gabble(id = randomUUIDAsString(), text = "Gabble 4 text", createdById = 1, createdOn = LocalDateTime.now())
        val GABBLE_5 = Gabble(id = randomUUIDAsString(), text = "Gabble 5 text", createdById = 1, createdOn = LocalDateTime.now())
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
            logger.info("Database alread contains Gabble objects")
        }
    }
}

@Controller
class HomeController {
    @GetMapping(value = ["/home"])
    fun howdy(model: Model, principal: Principal): String {
        val authentication = principal as OAuth2Authentication
        val user = authentication.userAuthentication.details as Map<*, *>
        model.addAttribute("user", user)
        return "home"
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