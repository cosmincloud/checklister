node (label: 'jdk11') {
  stage("scm") {
    cleanWs
    checkout scm
  }
  stage("build") {
    sh "./gradlew clean build"
  }
}
