//package com.redgyro
//
//import com.github.fakemongo.Fongo
//import com.mongodb.MongoClient
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.env.Environment
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration
//
//@Configuration
//class TestConfiguration(private val env: Environment) : AbstractMongoConfiguration() {
//
//    override fun getDatabaseName(): String =
//            env.getProperty("mongo.db.name", "test")
//
//    override fun mongoClient(): MongoClient {
//        logger.info("Instantiating Fongo with name $databaseName.")
//        return Fongo(databaseName).mongo
//    }
//
//    companion object {
//        val logger: Logger = LoggerFactory.getLogger(TestConfiguration::class.java)
//    }
//}