package com.redgyro.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.redgyro.models.UserProfile
import com.redgyro.repositories.UserProfileRepository
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeAll
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

    @Autowired
    lateinit var userProfileRepository: UserProfileRepository

    @LocalServerPort
    var port: Int = 0

    companion object {
        val USER_PROFILE_1 = UserProfile(userId = "00ugl9afjiwNub6yt0h7", bio = "user 1 bio", location = "Helmond", website = "http://www.google.com")
        val USER_PROFILE_2 = UserProfile(userId = "00ugl9afjiwNub6yt0i8", bio = "user 2 bio", location = "Helmond", website = "http://www.google.com")
    }

    @BeforeAll
    fun setup() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port

        userProfileRepository.save(USER_PROFILE_1)
        userProfileRepository.save(USER_PROFILE_2)
    }

    @Test
    fun `get all users - success`() {
        RestAssured.get(UserProfilesController.BASE_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", Matchers.`is`(2))
    }

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
    fun `create new profile - userId already exists`() {

    }

    @Test
    @Disabled("TODO - Implement create new profile")
    fun `create new profile - success`() {
    }
}