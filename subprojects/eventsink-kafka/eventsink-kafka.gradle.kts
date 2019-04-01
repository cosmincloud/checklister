plugins {
    `java-library`
    kotlin("jvm") version "1.3.11"
}

repositories {
    mavenCentral()
}

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTestImplementation by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

configurations["integrationTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    shouldRunAfter("test")
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))

    // Checklister dependencies
    implementation(project(":dto"))
    implementation(project(":event"))
    testImplementation(project(":eventserde-json"))

    // log4j2
    implementation("org.apache.logging.log4j:log4j-api:2.11.2")
    testImplementation("org.apache.logging.log4j:log4j-core:2.11.2")

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")

    // Officially supported Kafka client
    implementation("org.apache.kafka:kafka-clients:2.1.1")
}