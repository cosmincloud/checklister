node (label: 'jdk11') {
  stage("scm") {
    checkout scm
  }
  stage("compile") {
    sh "./gradlew clean classes"
  }
}
