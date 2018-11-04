package com.redgyro

import com.redgyro.config.UserFeignClientInterceptor
import feign.RequestInterceptor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.openfeign.EnableFeignClients
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
