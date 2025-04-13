pipeline {
    agent any

    environment {
        BACKEND_DIR = './property-service' // Your Spring Boot project folder, or '.' if it's root
        SIT_SERVER = 'ubuntu@13.203.200.128' // Replace with your real SIT EC2 IP
        SIT_DEPLOY_PATH = '/home/ubuntu/property-service/' // Remote deploy path
        JAR_NAME = 'property-service.jar' // Final name for deployed JAR
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saka-do/rap-property-service.git', credentialsId: 'github-pat'
            }
        }

        stage('Build Spring Boot App') {
            steps {
                dir("${env.BACKEND_DIR}") {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Deploy to SIT') {
            steps {
                input message: 'Deploy the latest backend to SIT?', ok: 'Deploy Now'

                script {
                    // Find the generated .jar file name dynamically (excluding original)
                    def builtJar = sh(
                        script: "ls ${env.BACKEND_DIR}/target/*.jar | grep -v 'original' | head -n 1",
                        returnStdout: true
                    ).trim()

                    echo "Found JAR: ${builtJar}"

                    // Deploy the JAR to SIT
                    // Create directory on SIT and kill any running Java process (if needed)
                    // Copy the JAR to the SIT server and deploy as property-service.jar
                    // Start the Spring Boot app with nohup in the background
                    sh """
                        set -x
                        ssh ${env.SIT_SERVER} 'mkdir -p ${env.SIT_DEPLOY_PATH} && pkill -f "java -jar" || true'
                        echo "Found folder"

                        scp ${builtJar} ${env.SIT_SERVER}:${env.SIT_DEPLOY_PATH}${env.JAR_NAME}
                        echo "com 2 step"

                        ssh ${env.SIT_SERVER} 'nohup java -jar ${env.SIT_DEPLOY_PATH}${env.JAR_NAME} > ${env.SIT_DEPLOY_PATH}app.log 2>&1 &'
                        echo "Done"
                    """
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
    }
}
