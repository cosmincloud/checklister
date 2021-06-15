group = "cloud.cosmin.checklister"

plugins {
    kotlin("jvm") version "1.5.10" apply false
    // Annotation Processing with Kotlin: https://kotlinlang.org/docs/reference/kapt.html
    kotlin("kapt") version "1.5.10" apply false
    // https://kotlinlang.org/docs/reference/compiler-plugins.html#spring-support
    id("org.jetbrains.kotlin.plugin.spring") version "1.5.10" apply false
    // https://kotlinlang.org/docs/reference/compiler-plugins.html#jpa-support
    id("org.jetbrains.kotlin.plugin.jpa") version "1.5.10" apply false
    // Kotlin Serialization: https://github.com/Kotlin/kotlinx.serialization
    kotlin("plugin.serialization") version "1.5.10" apply false
}