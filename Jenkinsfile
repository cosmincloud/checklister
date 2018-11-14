//node (label: 'jdk11') {
node {
  stage("scm") {
    checkout scm
  }
  stage("compile") {
    sh "./gradlew clean classes"
  }
}
