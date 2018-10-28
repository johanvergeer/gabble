package com.redgyro.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.redgyro.StartupCommandLineRunner
import com.redgyro.controllers.GabbleController
import com.redgyro.models.Gabble
import com.redgyro.models.GabbleCreateDto
import com.redgyro.randomUUIDAsString
import com.redgyro.repositories.GabbleRepository
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.restassured.RestAssured
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Profile
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Profile("test")
@DirtiesContext
private class GabbleControllerIT {

    @Autowired
    lateinit var gabbleRepository: GabbleRepository

    @Autowired
    lateinit var jsonMapper: ObjectMapper

    @LocalServerPort
    var port: Int = 0

    companion object {
        val logger: Logger = LoggerFactory.getLogger(StartupCommandLineRunner::class.java)

        val GABBLE_1 = Gabble(id = randomUUIDAsString(), text = "Gabble 1 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
        val GABBLE_2 = Gabble(id = randomUUIDAsString(), text = "Gabble 2 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
        val GABBLE_3 = Gabble(id = randomUUIDAsString(), text = "Gabble 3 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
        val GABBLE_4 = Gabble(id = randomUUIDAsString(), text = "Gabble 4 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
        val GABBLE_5 = Gabble(id = randomUUIDAsString(), text = "Gabble 5 text", createdById = "00ugl9afjiwNub6yt0h7", createdOn = LocalDateTime.now())
    }


    @BeforeAll
    fun setup() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
    }

    @BeforeEach
    fun setupBeforeEach(){

        gabbleRepository.deleteAll()

        gabbleRepository.save(GABBLE_1)
        gabbleRepository.save(GABBLE_2)
        gabbleRepository.save(GABBLE_3)
        gabbleRepository.save(GABBLE_4)
        gabbleRepository.save(GABBLE_5)
    }

    @Test
    fun `get all gabbles - success`() {
        RestAssured.get(GabbleController.BASE_URL)
                .then()
                .assertThat()
                .body("size()", `is`(5))
    }

    @Test
    fun `create new gabble - success`() {
        val userId = "00ugl9afjiwNub6yt0h8"
        val text = "new gabble text"
        val newGabble = GabbleCreateDto(text = text, createdById = userId)
        val newGabbleJson = jsonMapper.writeValueAsString(newGabble)

        val request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(newGabbleJson)

        request.post(GabbleController.BASE_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("text", equalTo(text))
                .body("createdById", equalTo(userId))
                .body("tags.size()", equalTo(0))
    }

    @Test
    fun `create new gabble - success with tags`() {
        val userId = "00ugl9afjiwNub6yt0h8"
        val text = "new gabble text with #tag1 and #tag2"
        val newGabble = GabbleCreateDto(text = text, createdById = userId)
        val newGabbleJson = jsonMapper.writeValueAsString(newGabble)

        val request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(newGabbleJson)

        request.post(GabbleController.BASE_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .body("tags", containsInAnyOrder("tag1", "tag2"))
    }

    @Test
    fun `add like to gabble - success`() {
        RestAssured.post("${GabbleController.BASE_URL}/${GABBLE_1.id}/likes/00ugl9afjiwNub6yt0h8")

        val updatedGabble = gabbleRepository.findById(GABBLE_1.id).get()
        val likes = updatedGabble.likedBy
        likes.shouldHaveSize(1)
        likes.first() shouldBe "00ugl9afjiwNub6yt0h8"
    }

    @Test
    fun `add like to gabble - gabble not found`() {
        val gabbleId = randomUUIDAsString()

        RestAssured.post("${GabbleController.BASE_URL}/$gabbleId/likes/00ugl9afjiwNub6yt0h8")
                .then()
                .assertThat()
                .statusCode(404) // Not found
                .body("message", equalTo("Gabble with id $gabbleId not found"))
    }

    @Test
    fun `add like to gabble - cannot like own gabble`() {
        RestAssured.post("${GabbleController.BASE_URL}/${GABBLE_1.id}/likes/${GABBLE_1.createdById}")
                .then()
                .assertThat()
                .statusCode(406) // Not found
                .body("message", equalTo("User cannot like own Gabble"))
    }
}