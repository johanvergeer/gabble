package com.redgyro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher
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