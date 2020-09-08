pipeline {
  agent any
  stages {
    stage('information_print') {
      steps {
        echo 'Hello'
      }
    }

    stage('build') {
      steps {
        bat(script: 'mvn clean package', returnStatus: true, returnStdout: true)
      }
    }

  }
}