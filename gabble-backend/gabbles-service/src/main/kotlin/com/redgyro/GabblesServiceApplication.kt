package com.redgyro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class GabblesServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(GabblesServiceApplication::class.java)
            .run(*args)
}