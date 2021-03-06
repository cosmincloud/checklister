plugins {
    `java-library`
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.apache.logging.log4j:log4j-api:2.11.2")
    implementation(kotlin("stdlib-jdk8")) // Kotlin stdlib
    implementation(project(":event"))
}