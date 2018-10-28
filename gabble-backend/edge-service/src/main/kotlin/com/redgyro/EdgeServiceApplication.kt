package com.redgyro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.ribbon.RibbonClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime


@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
class EdgeServiceApplication

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