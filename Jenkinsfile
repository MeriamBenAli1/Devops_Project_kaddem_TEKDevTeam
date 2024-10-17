pipeline {
    agent any
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


stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }







    post {
        success {
            echo 'Le build a réussi!'
        }
        failure {
            echo 'Le build a échoué!'
        }
    }
}