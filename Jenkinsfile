node (label: 'jdk11') {
  stage("scm") {
    cleanWs
    checkout scm
  }
  stage("compile") {
    sh "./gradlew clean classes"
  }
}
