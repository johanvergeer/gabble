package com.redgyro.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Profile
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Profile("test")
@DirtiesContext
class UserProfilesControllerIT {

    @Autowired
    lateinit var jsonMapper: ObjectMapper

    @LocalServerPort
    var port: Int = 0

    @Test
    @Disabled("TODO - Implement get profile by user id")
    fun `get user profile by user id - success`() {

    }

    @Test
    @Disabled("TODO - Implement user id not found")
    fun `get user profile by user id - not found`() {

    }

    @Test
    @Disabled("TODO - Implement create new profile when userId already exists")
    fun `create new profile - userId already exists`(){

    }

    @Test
    @Disabled("TODO - Implement create new profile")
    fun `create new profile - success`(){}
}