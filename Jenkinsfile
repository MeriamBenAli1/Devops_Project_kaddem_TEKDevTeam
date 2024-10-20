pipeline {
    agent any

    tools {
        maven 'M2_HOME'
    }
environment {
SONARQUBE_SERVER = 'SonarQube'
}
    stages {
    stage('Checkout') {
                steps {

                    git branch: 'rima',
                        credentialsId: '853bbb17-ff9a-4e63-a294-d37d30f791a9',
                        url: 'https://github.com/MeriamBenAli1/Devops_Project_kaddem_TEKDevTeam.git'
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
        stage('SonarQube Analysis') {
                    steps {
                        script {
                            withSonarQubeEnv('SonarQube') {
                            sh 'mvn sonar:sonar -Dsonar.projectKey=rimaBackend'
                                }

                        }
                    }
                }
        stage('Docker Build') {
            steps {
                script {
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: '22e454af-c74f-4685-b178-4983efe9baed', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD']]) {
                        sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                        // Utiliser le numéro de build comme tag pour l'image
                        def imageTag = "myapp:${env.BUILD_NUMBER}"
                        sh "docker build -t ${imageTag} ."
                    }
                }
            }
        }

    }
}
