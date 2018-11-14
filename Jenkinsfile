node (label: 'jdk11') {
  stage("scm") {
    checkout scm
  }
  stage("hello") {
    sh "./gradlew clean classes"
  }
}
