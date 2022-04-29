pipeline {
    agent any

    stages {
        stage("build") {
            steps {
                echo 'Building...'
                script {
                    sh './gradlew clean build'
                    sh 'docker build -t grunclepug/grunclebot:latest .'
                }
            }
        }

        stage("deploy") {
            steps {
                echo 'Deploying...'
                script {
                    sh 'docker run -d -v ./src/main/resources:/src/main/resources grunclepug/grunclebot:latest'
                }
            }
        }
    }
}