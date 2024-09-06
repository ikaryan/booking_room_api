pipeline {
    agent any
    tools {
        maven 'jenkins-maven' // Name of the Maven installation in Jenkins tools configuration
    }
    parameters {
        choice(name: 'TYPE', choices: ['Regression', 'Sanity', 'Unit'], description: 'Select the type of test')
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ikaryan/booking_room_api.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                 sh 'mvn clean test'
            }
//             steps {
//                 script{
//                     if(params.TYPE == 'Unit') {
//                        sh 'mvn clean verify -Dcucumber.filter.tags=@Test'
//                     } else if(params.TYPE == 'Regression') {
//                       sh 'mvn clean verify -Dcucumber.filter.tags=@Regression && @Test'
//                     } else {
//                       sh 'mvn clean verify -Dcucumber.filter.tags=@Sanity && @Test'
//                     }
//                 }
            }
            post {
                always {
                    junit './target/surefire-reports/*.xml' // Collect JUnit test results
                }
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }

    post {
        success {
            echo 'Build successful!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}