pipeline{
    agent any
    
    tools {
        jdk 'Java17'
        maven 'Maven3'
    }
    
    stages{
        stage("Cleanup Workspace"){
            steps {
                cleanWs()
            }

        }
        stage("Checkout from SCM"){
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/maybtdevops/Devops-Mega-project'
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

}


