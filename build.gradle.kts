import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import io.swagger.v3.plugins.gradle.tasks.ResolveTask.Format.JSON

plugins {
    id("java")
    id("com.gradle.build-scan") version "2.0.2"
    id("org.springframework.boot") version "2.1.1.RELEASE"
    kotlin("jvm") version "1.3.11"
    // https://kotlinlang.org/docs/reference/compiler-plugins.html#spring-support
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.11"
    // https://kotlinlang.org/docs/reference/compiler-plugins.html#jpa-support
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.11"
    // https://github.com/swagger-api/swagger-core/tree/master/modules/swagger-gradle-plugin
    id("io.swagger.core.v3.swagger-gradle-plugin") version "2.0.6"
}

repositories {
    mavenCentral()
}

group = "cloud.cosmin.checklister"
version = "0.0.8"

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks

compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

// unchanging name for boot JAR (referenced in Dockerfile)
val bootJar: BootJar by tasks
bootJar.archiveName = "app.jar"

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

tasks.test {
    useJUnitPlatform()
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

tasks.resolve {
    outputFileName = "PetStoreAPI"
    outputFormat = JSON
    prettyPrint = true
    classpath = sourceSets["main"].runtimeClasspath
    resourcePackages = setOf("io.test")
    outputPath = "test"
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))

    // Spring Boot
    implementation(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:2.1.1.RELEASE"))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Swagger
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")

    // database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-java8")
    runtime("org.postgresql:postgresql:42.2.2")
    implementation("org.flywaydb:flyway-core:5.0.7")

    implementation("org.bitbucket.cowwoc:requirements-core:4.0.4-RC")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.7")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")

    // because of Java 9+
    // https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception-in-j#43574427
    // https://blog.sourced-bvba.be/article/2017/12/17/java9-spring/
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.0")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.0")
    implementation("javax.activation:activation:1.1.1")

    // JSON Patch
    implementation("com.github.fge:json-patch:1.9")

    // dev tools
    implementation("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")

    // Support running JUnit 4 tests using JUnit 5
    testImplementation("junit:junit:4.12")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.3.2")

    // H2 in-memory database
    testImplementation("com.h2database:h2:1.4.197")

//    integrationTestCompile("org.seleniumhq.selenium:selenium-java:3.13.0")
//    integrationTestCompile("org.seleniumhq.selenium:selenium-remote-driver:3.13.0")

    implementation(project("modules:buildconfig"))
}
