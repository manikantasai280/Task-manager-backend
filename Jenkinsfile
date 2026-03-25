pipeline {
    agent any

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/manikantasai280/Task-manager-backend.git'
            }
        }

        stage('Build Project') {
            steps {
                bat '''
                set JAVA_HOME=C:\\Program Files\\Eclipse Adoptium\\jdk-17.0.18.8-hotspot
                set PATH=%JAVA_HOME%\\bin;%PATH%
                "C:\\Users\\manik\\Downloads\\apache-maven-3.9.14-bin\\apache-maven-3.9.14\\bin\\mvn.cmd" clean package
                '''
            }
        }

        stage('Verify Build') {
            steps {
                bat 'dir target'
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
