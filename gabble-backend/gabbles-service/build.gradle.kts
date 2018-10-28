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

    springBootStarter("actuator")
    springBootStarter("data-mongodb")
//    springBootStarter("security")
    springBootStarter("tomcat")
    springBootStarter("web")

//    springCloud("config-client")
    springCloud("starter-netflix-eureka-client")
//    springCloud("starter-netflix-zuul")
//    springCloud("starter-sleuth")

    compile("com.google.code.gson:gson:2.8.5") // Added because of issues with starter-netflix-eureka-client
    compile("io.jsonwebtoken:jjwt:0.9.1")

    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testCompile("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testCompile("io.kotlintest:kotlintest-runner-junit5:3.1.10")
    testCompile("io.kotlintest:kotlintest-extensions-spring:3.1.10")
    testCompile("org.springframework.boot:spring-boot-starter-test:2.0.5.RELEASE")
    testCompile("com.github.fakemongo:fongo:2.2.0-RC1")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}