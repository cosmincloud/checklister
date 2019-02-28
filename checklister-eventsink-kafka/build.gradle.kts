plugins {
    `java-library`
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":checklister-dto"))
    implementation(project(":checklister-event"))
}