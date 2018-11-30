import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.springframework.boot") version "2.0.6.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.2.71"
}

val springBootVersion: String by parent!!.project
val springCloudVersion: String by parent!!.project

fun DependencyHandlerScope.springBoot(module: String) = this.compile("org.springframework.boot:spring-boot-$module:$springBootVersion")
fun DependencyHandlerScope.springCloud(module: String, version: String = springCloudVersion) = this.compile("org.springframework.cloud:spring-cloud-$module:$version")
fun DependencyHandlerScope.springBootStarter(module: String) = this.springBoot("starter-$module")

dependencies {
    springBoot("devtools")

    springBootStarter("activemq")
    springBootStarter("actuator")
    springBootStarter("data-jpa")
    springBootStarter("security")
    springBootStarter("web")

    compile("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:$springBootVersion")

    compile("io.jsonwebtoken:jjwt:0.9.1")
    compile("org.postgresql:postgresql:42.2.5")
    compile("org.apache.activemq:activemq-broker:5.15.8")
    compile("com.fasterxml.jackson.core:jackson-databind:2.9.7")

    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testCompile("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.10")
    testCompile("io.kotlintest:kotlintest-extensions-spring:3.1.10")
    testCompile("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testCompile("io.rest-assured:rest-assured:3.2.0")
    testCompile("org.hamcrest:hamcrest-all:1.3")
    testCompile("com.h2database:h2:1.4.197")

    compile(project(":gabble-backend:gabble-models"))
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}