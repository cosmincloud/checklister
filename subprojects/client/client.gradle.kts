plugins {
    // https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-gradle-plugin/README.adoc
    id("org.openapi.generator") version "4.2.2"
}

openApiGenerate {
    generatorName.set("java")
    inputSpec.set("${project.rootDir}/openapi.json")
    outputDir.set("${project.buildDir}/openapi")

    generateApiTests.set(false)
    generateModelTests.set(false)

    // Ignore file doesn't work as expected.
    //val ignoreFile = "${project.projectDir}/openapi-generator-ignore"
    //println("ignoreFileOverride = " + ignoreFile)
    //ignoreFileOverride.set(ignoreFile)

    configOptions.set(
      mapOf(
        "invokerPackage" to "cloud.cosmin.checklister.client",
        "apiPackage"     to "cloud.cosmin.checklister.client.api",
        "modelPackage"   to "cloud.cosmin.checklister.client.model",
        "groupId"        to "cosmin.cloud.checklister",
        "artifactId"     to "client",
        "dateLibrary"    to "java8"
      )
    )
}
