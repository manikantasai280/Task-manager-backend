pipeline {
    agent any

    environment {
        MAVEN_HOME = "C:\\Program Files\\Apache\\maven"   // change if needed
        PATH = "${MAVEN_HOME}\\bin;${env.PATH}"
    }

    stages {

        stage('Clone Repository') {
            steps {
                git 'https://github.com/manikantasai280/Task-manager-backend.git'
            }
        }

        stage('Build Project') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Package JAR') {
            steps {
                bat 'mvn package'
            }
        }

        stage('Verify JAR') {
            steps {
                script {
                    if (fileExists('target')) {
                        bat 'dir target'
                        echo 'SUCCESS: JAR file created'
                    } else {
                        error 'FAILED: target folder not found'
                    }
                }
            }
        }

        stage('DEB Package (Not Supported)') {
            steps {
                echo 'Skipping .deb creation (Windows does not support it)'
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
