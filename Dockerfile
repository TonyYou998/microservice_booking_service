FROM openjdk:11-jdk-slim as build
MAINTAINER uit.com
copy target/microservice_booking_service-0.0.1-SNAPSHOT.jar  microservice_booking_service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","microservice_booking_service-0.0.1-SNAPSHOT.jar"]