package com.redgyro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class SpringCloudConfigServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(SpringCloudConfigServerApplication::class.java)
            .run(*args)
}
