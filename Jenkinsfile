pipeline {
    agent any

    stages {
        stage("Build") {
            steps {
                sh './gradlew clean build'
            }
        }

        stage("Deploy") {
            sh 'java -jar build/libs/GruncleBot-1.0-all.jar'
        }
    }
}