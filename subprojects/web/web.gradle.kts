import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.net.URI

plugins {
    id("java")
    id("org.springframework.boot") version "2.1.3.RELEASE"
    kotlin("jvm")
    // Annotation Processing with Kotlin: https://kotlinlang.org/docs/reference/kapt.html
    kotlin("kapt")
    // https://kotlinlang.org/docs/reference/compiler-plugins.html#spring-support
    id("org.jetbrains.kotlin.plugin.spring")
    // https://kotlinlang.org/docs/reference/compiler-plugins.html#jpa-support
    id("org.jetbrains.kotlin.plugin.jpa")
    // https://github.com/swagger-api/swagger-core/tree/master/modules/swagger-gradle-plugin
    //id("io.swagger.core.v3.swagger-gradle-plugin") version "2.1.9"
}

repositories {
    mavenCentral()
    jcenter()

//    maven {
//        url = URI.create("https://dl.bintray.com/arrow-kt/arrow-kt/")
//    }
}

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks

compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

kapt {
    useBuildCache = false
}

// unchanging name for boot JAR (referenced in Dockerfile)
val bootJar: BootJar by tasks
bootJar.archiveFileName.set("app.jar")

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

val bannerFile = "${project.projectDir.canonicalPath}/src/main/resources/banner.txt"
val generateBanner = tasks.register("generateBanner") {
    outputs.file(bannerFile)

    outputs.upToDateWhen {
        val file = File(bannerFile)
        val projectVersion = project.version.toString()

        file.exists() && file.readText().contains(projectVersion)
    }

    doLast {
        val versionLine = project.version.toString() + " ".repeat(32 - project.version.toString().length)
        File(bannerFile).printWriter().use { out ->
            out.println("""
                ╔═══════════════════════════════════╗
                ║  ╔═╗╦ ╦╔═╗╔═╗╦╔═╦  ╦╔═╗╔╦╗╔═╗╦═╗  ║
                ║  ║  ╠═╣║╣ ║  ╠╩╗║  ║╚═╗ ║ ║╣ ╠╦╝  ║
                ║  ╚═╝╩ ╩╚═╝╚═╝╩ ╩╩═╝╩╚═╝ ╩ ╚═╝╩╚═  ║
                ║  v${versionLine}║
                ╚═══════════════════════════════════╝
            """.trimIndent())
        }
    }
}

val cleanBanner = tasks.register("cleanBanner") {
    destroyables.register(bannerFile)

    outputs.upToDateWhen {
        val file = File(bannerFile)
        !file.exists()
    }

    doLast {
        delete(bannerFile)
    }
}

val buildPropertiesFile = "${project.projectDir.canonicalPath}/src/main/resources/checklister-build.properties"
val generateBuildConfigResource = tasks.register("generateBuildConfigResource") {
    val propertiesFile = File(buildPropertiesFile)
    val projectVersion = project.version.toString()

    // TODO: Figure out how to do up-to-date checks in Kotlin DSL
    val isUpToDate = propertiesFile.exists() && propertiesFile.readText().contains(projectVersion)

    if (!isUpToDate) {
        outputs.file(buildPropertiesFile)

        File(buildPropertiesFile).printWriter().use { out ->
            out.println("version=${project.version}")
        }
    }
}

val cleanBuildConfigResource = tasks.register("cleanBuildConfigResource") {
    destroyables.register(buildPropertiesFile)
    doLast {
        delete(buildPropertiesFile)
    }
}

val processResources = tasks.named<ProcessResources>("processResources")
processResources {
    dependsOn(generateBanner, generateBuildConfigResource)
}

tasks {
    clean {
        dependsOn(cleanBanner, cleanBuildConfigResource)
    }
}

val arrow_version: String = "0.13.2"

dependencies {
    // Kotlin
    implementation(kotlin("stdlib-jdk8"))

    // Spring Boot
    implementation(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:2.1.3.RELEASE"))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // OpenAPI: https://springdoc.org
    implementation("org.springdoc:springdoc-openapi-ui:1.5.9")

    // database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-java8")
    runtimeOnly("org.postgresql:postgresql:42.2.2")
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

    // Arrow Kt
    implementation("io.arrow-kt:arrow-core:$arrow_version")
    //implementation("io.arrow-kt:arrow-syntax:$arrow_version")
    kapt("io.arrow-kt:arrow-meta:$arrow_version")

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

    implementation(project(":event"))
    implementation(project(":dto"))
    implementation(project(":eventserde-json"))
    implementation(project(":eventsink-kafka"))
    implementation(project(":eventsink-logger"))
}

