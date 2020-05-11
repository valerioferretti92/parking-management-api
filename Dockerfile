FROM openjdk:13

MAINTAINER Valerio Ferretti <valerio.ferretti92@gmail.com>

WORKDIR .

COPY target/parking-management-api-1.0-SNAPSHOT.jar /parking-management-api.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=container", "-jar", "/parking-management-api.jar"]

