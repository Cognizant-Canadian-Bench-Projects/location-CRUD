pipeline {
    agent any

    stages {
         stage('build') {
            steps {
                script {
                    try {
                        sh './gradlew clean build'
                    } catch (exec) {
                        throw exec
                    }
                }
            }
        }

        stage('unit-test') {
            steps {
                script {
                    try {
                        sh './gradlew test'
                    } catch (exec) {
                        throw exec
                    }
                }
            }
        }

        stage('integration-test') {
              when { not { changeRequest() } }
                steps {
                    script {
                        try {
                            sh './gradlew integrationTest'
                        } catch (exec) {
                            // this is so we can capture the results in 'finally' below
                            throw exec
                        }
                    }
                }
            }

        stage('Deploy') {
            when { branch 'main' }
            steps {
                echo 'Deploying....'
            }
        }
    }

    post {
        success {
             mergePullRequest()
        }
        failure {
            commentPullRequest("[Failing Build](${env.BUILD_URL})")
        }
    }
}