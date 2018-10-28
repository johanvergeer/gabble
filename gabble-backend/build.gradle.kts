import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.71"
    base
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    subprojects.forEach {
        archives(it)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

subprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    apply {
        plugin("kotlin")
    }

    dependencies {
        compile(kotlin("stdlib-jdk8"))
        compile(kotlin("reflect")) // Required by Spring Boot
    }
}