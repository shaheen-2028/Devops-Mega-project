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

}