// this is in a sub-project because the buildconfig plugin doesn't work well
// with the kotlin plugin
plugins {
    // https://github.com/mfuerstenau/gradle-buildconfig-plugin
    id("de.fuerstenau.buildconfig") version "1.1.8"
}


buildConfig {
    packageName = "cloud.cosmin.checklister"
}
