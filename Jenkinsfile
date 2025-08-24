pipeline{
    agent any
    
    tools {
        jdk 'Jdk17'
        maven 'Maven3'
    }
stages{
        stage("Cleanup Workspace"){
            steps {
                cleanWs()
            }

        }
}
        stage("Checkout from SCM"){
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/shaheen-2028/Devops-Mega-project.git'
            }

        }


        stage("Build the application"){
            steps {
               sh "mvn clean package"
            }

        }

        stage("Test the application"){
            steps {
               sh "mvn test"
            }
        }


}
stage("SonarQube Analysis") {
    steps {
        script {
            withSonarQubeEnv('MySonarQube') {  // must match Jenkins global config name
                sh "mvn sonar:sonar \
                      -Dsonar.projectKey=Devops-Mega-project \
                      -Dsonar.projectName='Devops Mega Project' \
                      -Dsonar.sources=src/main/java/com/dmancloud/dinesh/demoapp/issues
                      -Dsonar.login=$SONAR_AUTH_TOKEN"
            }
        }
    }
}

stage("Quality Gate") {
    steps {
        script {
            timeout(time: 2, unit: 'MINUTES') {
                def qg = waitForQualityGate() 
                if (qg.status != 'OK') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
            }
        }
    }
}

         stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('',DOCKER_PASS) {
                        docker_image = docker.build "${IMAGE_NAME}"
                     }

                     docker.withRegistry('',DOCKER_PASS) {
                         docker_image.push("${IMAGE_TAG}")
                         docker_image.push('latest')
                     }
                 }
             }

         }

         stage("Trivy Scan") {
             steps {
                 script {
		    sh ('docker run -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy image mydevopsuser46/devops-mega-project:latest  --no-progress --scanners vuln  --exit-code 0 --severity HIGH,CRITICAL --format table')
                 }
             }

         }

         stage ('Cleanup Artifacts') {
           steps {
             script {
                   sh "docker rmi ${IMAGE_NAME}:${IMAGE_TAG}"
                   sh "docker rmi ${IMAGE_NAME}:latest"
        }
           }
        }
        
        stage("Trigger CD Pipeline") {
            steps {
                script {
                   sh "curl -v -k --user admin:${JENKINS_API_TOKEN} -X POST -H 'cache-control: no-cache' -H 'content-type: application/x-www-form-urlencoded' --data 'IMAGE_TAG=${IMAGE_TAG}' 'http://13.126.66.110:8080/job/Gitops-devops-mega-project/buildWithParameters?token=gitops-token'"
               }
           }

        }

     }

    post {
          success {
              emailext (
                  to: 'devopsstudy09@gmail.com',
                  subject: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                  body: """<p>Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' succeeded.</p><p>Check console output at <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></p>""",
                  mimeType: 'text/html'
              )
          }
    }
          failure {
              emailext (
                  to: 'devopsstudy09@gmail.com',
                  subject: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                  body: """<p>Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' failed.</p><p>Check console output at <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></p>""",
                  mimeType: 'text/html'
               )
        }

       }
}
