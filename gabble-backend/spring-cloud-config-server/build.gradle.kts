import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.springframework.boot") version "2.0.6.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.2.71"
}

val springBootVersion: String by parent!!.project
val springCloudVersion: String by parent!!.project

fun DependencyHandlerScope.springBoot(module: String) = this.compile("org.springframework.boot:spring-boot-$module:$springBootVersion")
fun DependencyHandlerScope.springBootStarter(module: String) = this.springBoot("starter-$module")

dependencies {
    springBoot("devtools")

    compile("org.springframework.cloud:spring-cloud-config-server:$springCloudVersion")
}