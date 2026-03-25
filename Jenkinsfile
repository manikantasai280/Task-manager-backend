pipeline {
    agent any

    stages {

        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/manikantasai280/Task-manager-backend.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }
    }

    post {
        success {
            echo 'SUCCESS'
        }
        failure {
            echo 'FAILED'
        }
    }
}
