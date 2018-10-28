//package com.redgyro.services
//
//import io.kotlintest.shouldBe
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//
//class GabbleServiceIT: GabbleTestWithFongo() {
//
//    @Autowired
//    lateinit var gabbleService: GabbleService
//
//    @Test
//    fun `validate test data loaded`(){
//        gabbleService.findAllGabbles().count() shouldBe 5
//    }
//}