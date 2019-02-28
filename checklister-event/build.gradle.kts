plugins {
    `java-library`
    kotlin("jvm") version "1.3.11"
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":checklister-dto"))
}