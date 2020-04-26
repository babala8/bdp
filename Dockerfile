FROM 10.2.21.95:10001/zj-jre-fonts:1.8.0_211
VOLUME /tmp
ADD treasury-brain-1.2.0-SNAPSHOT.jar /opt/app.jar
ADD resources /opt/resources
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Xms4096m -Xmx4096m","-jar","/opt/app.jar"]

# docker build -t 10.2.21.95:10001/treasury-brain:1.1.0-SNAPSHOT ./build/libs
