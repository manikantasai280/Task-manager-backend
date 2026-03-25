pipeline {
    agent any

    stages {

        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/manikantasai280/Task-manager-backend.git'
            }
        }

        stage('Build Project') {
            steps {
                bat '"C:\\Program Files\\Apache\\maven\\bin\\mvn.cmd" clean package'
            }
        }

        stage('Verify JAR') {
            steps {
                script {
                    if (fileExists('target')) {
                        bat 'dir target'
                        echo 'SUCCESS: JAR file created'
                    } else {
                        error 'FAILED: JAR file not found'
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'FINAL STATUS: SUCCESS'
        }
        failure {
            echo 'FINAL STATUS: FAILED'
        }
    }
}
