node {
  stage("scm") {
    checkout scm
  }
  stage("hello") {
    sh "./gradlew clean test"
  }
}
