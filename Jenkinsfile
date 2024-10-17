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
                script {
                    dir('DevOps_Project') {
                        sh 'mvn clean install'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    dir('DevOps_Project') {
                        // Exécute les tests avec Maven
                        sh 'mvn test'
                    }
                }
            }
        }
    }
}