FROM gradle:6.7.1-jdk11 AS build-stage
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim

RUN mkdir /app
COPY --from=build-stage /home/gradle/src/build/libs/*.jar /app/book-application-backend.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/book-application-backend.jar"]
