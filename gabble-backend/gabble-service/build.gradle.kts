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
    compile("ch.qos.logback:logback-classic:$logback_version")

    compile("io.ktor:ktor-auth:$ktor_version")
    compile("io.ktor:ktor-auth-jwt:$ktor_version")
    compile("io.ktor:ktor-client-apache:$ktor_version")
    compile("io.ktor:ktor-client-core:$ktor_version")
    compile("io.ktor:ktor-client-gson:$ktor_version")
    compile("io.ktor:ktor-client-json:$ktor_version")
    compile("io.ktor:ktor-gson:$ktor_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-server-host-common:$ktor_version")
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("io.ktor:ktor-websockets:$ktor_version")

    compile("com.google.code.gson:gson:2.8.5")
    compile("com.google.inject:guice:4.2.2")
    compile("org.apache.activemq:activemq-all:5.15.8")

    testCompile("io.ktor:ktor-server-tests:$ktor_version")

    compile(project(":gabble-backend:gabble-models"))
}
