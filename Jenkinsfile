pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                sh 'mvn verify'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
                echo 'Build OK'
            }
        }
    }
}