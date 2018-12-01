node (label: 'jdk8') {
  stage("scm") {
    cleanWs()
    checkout scm
  }
  stage("build") {
    sh "./gradlew clean build"
  }
}
