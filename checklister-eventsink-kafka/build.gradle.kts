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

    // Checklister dependencies
    implementation(project(":checklister-dto"))
    implementation(project(":checklister-event"))

    // Officially supported Kafka client
    implementation("org.apache.kafka:kafka-clients:2.1.1")
}