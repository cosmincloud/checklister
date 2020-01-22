rootProject.name = "checklister"

include("dto",
        "event",
        "eventserde-json",
        "eventsink-logger",
        "eventsink-kafka",
        "web",
        "history",
        "integrationTest",
        "client")

for (project in rootProject.children) {
    project.apply {
        projectDir = file("subprojects/$name")
        buildFileName = "$name.gradle.kts"
    }
}