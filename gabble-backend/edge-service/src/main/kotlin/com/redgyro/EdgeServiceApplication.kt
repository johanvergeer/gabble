package com.redgyro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.ribbon.RibbonClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager.authenticated
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails
import org.springframework.security.core.context.SecurityContextHolder
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Bean


@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableZuulProxy
@EnableOAuth2Sso
@SpringBootApplication
class EdgeServiceApplication {

    @Bean
    fun getUserFeignClientInterceptor(): RequestInterceptor = UserFeignClientInterceptor()
}

fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(EdgeServiceApplication::class.java)
            .run(*args)
}

data class Gabble(
        val id: String = "",
        val text: String = "",
        val createdById: Long = 0,
        val createdOn: LocalDateTime? = null,
        val tags: Set<String> = setOf(),
        val likedBy: Set<Long> = setOf(),
        val mentions: Set<Long> = setOf()
)

@Component
@FeignClient(value = "gabbles-service")
@RibbonClient(value = "gabbles-service")
interface GabbleClient {

    @GetMapping(value = ["/gabbles"])
    fun getAllGabbles(): List<Gabble>
}

@RestController
@RequestMapping(value = [GabbleController.BASE_URL])
class GabbleController(private val gabbleClient: GabbleClient) {

    companion object {
        const val BASE_URL = "/gabbles"
    }

    @GetMapping
    fun getAllGabbles() = gabbleClient.getAllGabbles()
}

@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.requestMatcher(RequestHeaderRequestMatcher("Authorization"))
                .authorizeRequests()
                .antMatchers("/**").authenticated()
    }
}

@Component
class UserFeignClientInterceptor : RequestInterceptor {

    override fun apply(template: RequestTemplate) {
        val securityContext = SecurityContextHolder.getContext()
        val authentication = securityContext.authentication

        if (authentication != null && authentication.details is OAuth2AuthenticationDetails) {
            val details = authentication.details as OAuth2AuthenticationDetails
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, details.tokenValue))
        }
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_TOKEN_TYPE = "Bearer"
    }
}
