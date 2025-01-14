pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "maven3.9.9"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                 git branch: 'main', 
                    credentialsId: '8cc42aff-9100-4ea7-bf64-ce70c58b22b6', 
                    url: 'https://github.com/ecommerce-order-app/ecommerce-productservice.git'
                // git 'https://github.com/ecommerce-order-app/ecommerce-productservice.git'

                // Run Maven on a Unix agent.
                sh "mvn clean install -Dmaven.test.failure.ignore=true"
                // Publish JUnit test results
        junit testResults: '**/target/surefire-reports/*.xml'

        // Publish JaCoCo coverage report
        publishCoverage adapters: [jacocoAdapter('**/target/site/jacoco/*.xml')], 
                        sourceFileResolver: sourceFiles('NEVER_STORE')

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        stage("CVC"){
            steps{
                sh "cd /var/jenkins_home/workspace/Test/"
                dependencyCheck additionalArguments: '--noupdate --scan=\'./**/*.jar\' --format XML --project Test', odcInstallation: 'OWASP Dependency-Check Plugin'
                dependencyCheckPublisher pattern: '$WORKSPACE/dependency-check-report.xml'
                archiveArtifacts allowEmptyArchive:true , artifacts: 'dependency-check-report.xml', onlyIfSuccessful: true
            }
        }
    }
}
