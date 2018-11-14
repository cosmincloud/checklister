//node (label: 'jdk11') {
node {
  stage("scm") {
    checkout scm
  }
  stage("hello") {
    sh "./gradlew clean classes"
  }
}
