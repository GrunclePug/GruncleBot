pipeline {
    agent any

    stages {
        stage("Build") {
            steps {
                configFileProvider(
                    [configFile(fileId: 'config', variable: 'src/resources')]
                ) {}
                sh './gradlew clean build'
            }
        }

        stage("Deploy") {
            steps {
                sh 'java -jar build/libs/GruncleBot-1.0-all.jar'
            }
        }
    }
}