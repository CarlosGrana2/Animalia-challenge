FROM adoptopenjdk:11-jre-hotspot
MAINTAINER cgranadosmontenegro@gmail.com
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} animalia-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/animalia-0.0.1-SNAPSHOT.jar"]