pipeline {
    agent any

    // agent {
    //     docker {
    //         // image 'maven:3-alpine'
    //         // image '10.2.21.95:10001/zj-gradle:4.10.3'
    //         image 'gradle:4.10.3-jdk8'
    //         args '-v gradle-cache:/home/gradle/.gradle'
    //         args '--privileged=true'
    //         args '-v /var/run/docker.sock:/var/run/docker.sock'
    //         args '-v /bin/docker:/bin/docker'
    //     }
    // }

    environment {
        APP_PREFIX = "10.2.21.95:10001"
        APP_NAME = 'treasury-brain'
        APP_VERSION  = '1.2.0-SNAPSHOT'
    }
    stages {
         stage('Build by Gradle') {
            steps {
                 script {
                     sh 'pwd'
                     sh 'ls -al'
                     //sh 'cp init.gradle /root/.gradle/'
                     //sh 'cp gradle.properties /root/.gradle/'
                     sh 'ls -al /root/.gradle/'
                     sh '/opt/gradle-4.10.3/bin/gradle clean -Dmod=single '
                     sh '/opt/gradle-4.10.3/bin/gradle bootjar -Dmod=single '
                 }
             }
         }
         stage('Build by Docker') {
             steps {
                 script {
                     sh 'pwd'
                     sh 'ls -al'
                     sh 'cp Dockerfile ./build/libs/'
                     sh 'cp -r ./resources ./build/libs/'
                     sh 'ls -al ./build/libs'
                     sh 'docker build -t ${APP_PREFIX}/${APP_NAME}:${APP_VERSION} ./build/libs'
                     // 注释了推送到私服仓库
                     // sh 'docker login -u publisher -p publisher ${APP_PREFIX}'
                     // sh 'docker push ${APP_PREFIX}/${APP_NAME}:${APP_VERSION}'
                 }
             }
         }
         stage('Run') {
             steps {
                 script {
                     result = sh (script: "docker ps -aq --filter name=${APP_NAME}", returnStdout: true).trim()
                     if(result != ""){
                         sh "docker stop `docker ps -aq --filter name=${APP_NAME}` "
                         sh "docker rm   `docker ps -aq --filter name=${APP_NAME}` "
                     }
                     sh 'docker run -d --name ${APP_NAME} -p 9090:8080 -v /etc/localtime:/etc/localtime ${APP_PREFIX}/${APP_NAME}:${APP_VERSION}'
                 }
             }
         }
    }
}
