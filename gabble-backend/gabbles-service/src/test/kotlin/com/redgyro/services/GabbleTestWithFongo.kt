package com.redgyro.services

import com.github.fakemongo.junit.FongoRule
import com.redgyro.models.Gabble
import com.redgyro.repositories.GabbleRepository
import org.junit.Rule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.*

fun randomUUIDAsString() = UUID.randomUUID().toString()

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
abstract class GabbleTestWithFongo(val initializeTestData: Boolean = true) {
    @Autowired
    lateinit var gabbleRepository: GabbleRepository

    @get:Rule
    val fongoRule = FongoRule()

    @BeforeAll
    fun setup() {
        if (initializeTestData){
            gabbleRepository.save(GABBLE_1)
            gabbleRepository.save(GABBLE_2)
            gabbleRepository.save(GABBLE_3)
            gabbleRepository.save(GABBLE_4)
            gabbleRepository.save(GABBLE_5)
        }
    }

    companion object {
        val GABBLE_1 = Gabble(id = randomUUIDAsString(), text = "Gabble 1 text", createdById = 1, createdOn = LocalDateTime.now())
        val GABBLE_2 = Gabble(id = randomUUIDAsString(), text = "Gabble 2 text", createdById = 1, createdOn = LocalDateTime.now())
        val GABBLE_3 = Gabble(id = randomUUIDAsString(), text = "Gabble 3 text", createdById = 1, createdOn = LocalDateTime.now())
        val GABBLE_4 = Gabble(id = randomUUIDAsString(), text = "Gabble 4 text", createdById = 1, createdOn = LocalDateTime.now())
        val GABBLE_5 = Gabble(id = randomUUIDAsString(), text = "Gabble 5 text", createdById = 1, createdOn = LocalDateTime.now())
    }
}