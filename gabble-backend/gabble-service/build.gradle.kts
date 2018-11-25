import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val logback_version: String by project

plugins {
    java
    application
    kotlin("jvm")
}

application {
    mainClassName = "io.ktor.server.netty.DevelopmentEngine"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("ch.qos.logback:logback-classic:$logback_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-server-host-common:$ktor_version")
    compile("io.ktor:ktor-auth:$ktor_version")
    compile("io.ktor:ktor-auth-jwt:$ktor_version")
    compile("io.ktor:ktor-gson:$ktor_version")

    compile("org.apache.activemq:activemq-all:5.15.8")

    testCompile("io.ktor:ktor-server-tests:$ktor_version")

    compile(project(":gabble-backend:gabble-models"))
}

kotlin.experimental.coroutines = Coroutines.ENABLE
