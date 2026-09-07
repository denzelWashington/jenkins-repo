pipeline {
    agent any

    stages {
    stage('Checkout') {
                steps {
                    echo 'Récupération du code source'
                    checkout scm
                }
            }

        stage('Test') {
            steps {
                sh 'mvn verify'
            }
        }

        stage('Build') {
                    steps {
                        echo 'Packaging de lapplication'
                        sh 'mvn package -DskipTests'
                    }
                }

                stage('Docker Build') {
                    steps {
                        echo 'Construction de l image Docker'
                        sh 'docker build -t jenkins-demo:latest .'
                    }
                }
    }
}