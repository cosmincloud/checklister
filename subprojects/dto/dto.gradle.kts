plugins {
    `java-library`
    kotlin("jvm")
    // Kotlin Serialization: https://github.com/Kotlin/kotlinx.serialization
    kotlin("plugin.serialization")
    // https://github.com/kotest/kotest-gradle-plugin
    id("io.kotlintest") version "1.1.1"
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))

    // Kotlin Serialization: https://github.com/Kotlin/kotlinx.serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")

    // KoTest: https://github.com/kotest/kotest
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.6.0")
}