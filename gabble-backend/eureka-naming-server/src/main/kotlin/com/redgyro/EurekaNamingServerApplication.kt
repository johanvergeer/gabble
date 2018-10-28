package com.redgyro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class EurekaNamingServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(EurekaNamingServerApplication::class.java)
            .run(*args)
}