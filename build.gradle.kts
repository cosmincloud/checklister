plugins {
    id("java")
    id("com.gradle.build-scan") version "2.0.2"
    id("org.springframework.boot") version "2.1.1.RELEASE"
}

repositories {
    mavenCentral()
}

group = "cloud.cosmin.checklister"
version = "0.0.6-SNAPSHOT"

dependencies {
    // Spring Boot
    implementation(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:2.1.1.RELEASE"))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-java8")
    runtime("org.postgresql:postgresql:42.2.2")
    implementation("org.flywaydb:flyway-core:5.0.7")

    implementation("org.bitbucket.cowwoc:requirements-core:4.0.4-RC")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.7")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.7")

    // because of Java 9+
    // https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception-in-j#43574427
    // https://blog.sourced-bvba.be/article/2017/12/17/java9-spring/
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.0")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.0")
    implementation("javax.activation:activation:1.1.1")

    // dev tools
    implementation("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")

//    integrationTestCompile("org.seleniumhq.selenium:selenium-java:3.13.0")
//    integrationTestCompile("org.seleniumhq.selenium:selenium-remote-driver:3.13.0")
}
