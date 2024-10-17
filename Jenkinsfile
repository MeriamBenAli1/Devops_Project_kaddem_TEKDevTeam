pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    // Assuming you have Docker installed and configured on your Jenkins agent
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: '22e454af-c74f-4685-b178-4983efe9baed', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD']]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        sh 'docker build -t myapp:1.0 .'
                    }
                }
            }
        }
    }
}
