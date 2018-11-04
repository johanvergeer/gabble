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
    springBootStarter("tomcat")
    springBootStarter("security")

    springCloud("config-client")
    springCloud("security")
    springCloud("starter-netflix-eureka-client")
    springCloud("starter-netflix-hystrix")
    springCloud("starter-netflix-zuul")
    springCloud("starter-openfeign")
    springCloud("starter-sleuth")

    compile("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:2.0.6.RELEASE")

    compile("com.google.code.gson:gson:2.8.5") // Added because of issues with starter-netflix-eureka-client
    compile("io.jsonwebtoken:jjwt:0.9.1")

    compile(project(":gabble-backend:gabble-models"))
}