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

    // jackson
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.9.8"))
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")

    // log4j2
    implementation("org.apache.logging.log4j:log4j-api:2.11.2")
    testImplementation("org.apache.logging.log4j:log4j-core:2.11.2")

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")

    implementation(project(":event"))
    testImplementation(project(":dto"))
}