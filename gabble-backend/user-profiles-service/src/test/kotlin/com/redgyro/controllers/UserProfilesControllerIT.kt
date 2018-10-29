package com.redgyro.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.redgyro.models.UserProfile
import com.redgyro.repositories.UserProfileRepository
import io.kotlintest.shouldBe
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo
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
        const val USER_1_ID = "00ugl9afjiwNub6yt0h7"
        const val USER_1_BIO = "user 1 bio"
        const val USER_1_LOCATION = "Eindhoven"
        const val USER_1_WEBSITE = "http://fontys.nl"
        val USER_PROFILE_1 = UserProfile(userId = USER_1_ID, bio = USER_1_BIO, location = USER_1_LOCATION, website = USER_1_WEBSITE)
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
    fun `get user profile by user id - success`() {
        RestAssured.get("${UserProfilesController.BASE_URL}/$USER_1_ID/")
                .then()
                .assertThat()
                .statusCode(200)
                .body("bio", equalTo(USER_1_BIO))
                .body("location", equalTo(USER_1_LOCATION))
                .body("website", equalTo(USER_1_WEBSITE))
    }

    @Test
    fun `get user profile by user id - not found`() {
        val userId = "00ugl9afjiwNub6ytlshdfs"

        RestAssured.get("${UserProfilesController.BASE_URL}/$userId/")
                .then()
                .assertThat()
                .statusCode(404) // Not found
                .body("message", equalTo("User profile for user with id $userId not found"))
    }

    @Test
    @DirtiesContext
    fun `create new profile - success`() {
        val userId = "00ugl9afjiwNub6yt0h8"
        val bio = "new user bio"
        val location = "Amsterdam"
        val website = "http://nu.nl"
        val newUserProfile = UserProfile(userId = userId, bio = bio, location = location, website = website)
        val newUserProfileJson = jsonMapper.writeValueAsString(newUserProfile)

        val request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(newUserProfileJson)

        request.post(UserProfilesController.BASE_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .body("bio", equalTo(bio))

        userProfileRepository.count() shouldBe 3
    }

    @Test
    fun `create new profile - userId already exists`() {
        val userId = USER_1_ID
        val bio = "new user bio"
        val location = "Amsterdam"
        val website = "http://nu.nl"
        val newUserProfile = UserProfile(userId = userId, bio = bio, location = location, website = website)
        val newUserProfileJson = jsonMapper.writeValueAsString(newUserProfile)

        val request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(newUserProfileJson)

        request.post(UserProfilesController.BASE_URL)
                .then()
                .assertThat()
                .statusCode(409) // Conflict
                .body("message", equalTo("User profile for user with id $USER_1_ID already exists"))

        userProfileRepository.count() shouldBe 2
    }
}