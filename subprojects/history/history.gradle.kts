import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.springframework.boot") version "2.1.3.RELEASE"
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks

compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))


    // Spring Boot
    implementation(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:2.1.3.RELEASE"))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Swagger
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")

    // database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-java8")
    runtimeOnly("org.postgresql:postgresql:42.2.2")
    implementation("org.flywaydb:flyway-core:5.0.7")
    
    // because of Java 9+
    // https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception-in-j#43574427
    // https://blog.sourced-bvba.be/article/2017/12/17/java9-spring/
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.0")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.0")
    implementation("javax.activation:activation:1.1.1")

}