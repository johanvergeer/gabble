package com.redgyro.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

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