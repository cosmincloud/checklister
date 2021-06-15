plugins {
    `java-library`
    kotlin("jvm")
    // https://github.com/avast/gradle-docker-compose-plugin
    id("com.avast.gradle.docker-compose") version "0.14.3"
    // https://github.com/kotest/kotest-gradle-plugin
    id("io.kotlintest") version "1.1.1"
}

repositories {
    mavenCentral()

    // jitpack required by Fuel
    maven(url = "https://jitpack.io") {
        name = "jitpack"
    }
}

val bootJar = tasks.getByPath(":web:bootJar")

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

val composeUp = tasks.getByName("composeUp")
composeUp.dependsOn(bootJar)
dockerCompose.isRequiredBy(test)

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))

    // KoTest: https://github.com/kotest/kotest
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.1.1")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.1.1")
    testImplementation("io.kotest:kotest-runner-console-jvm:4.1.1")

    // Fuel HTTP library: https://github.com/kittinunf/fuel
    testImplementation("com.github.kittinunf.fuel:fuel:2.0.1")

    // Fuel Jackson (de)serializer: https://github.com/kittinunf/fuel/blob/master/fuel-jackson/README.md
    testImplementation("com.github.kittinunf.fuel:fuel-jackson:2.0.1")

    // Jackson support for Java time classes: https://github.com/FasterXML/jackson-modules-java8
    testImplementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.9.0")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.9.0")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.0")

    implementation(project(":dto"))
}