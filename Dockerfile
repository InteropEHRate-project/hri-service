
FROM openjdk:15-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=hrindex-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

#------------multi stage build---------------
#FROM gradle:jdk11 AS builder
#COPY . /home/gradle/source
#WORKDIR /home/gradle/source
#RUN gradle build
#
#FROM openjdk:15-jdk-alpine
#COPY --from=builder /home/gradle/source/build/libs/hrindex-0.0.1-SNAPSHOT.jar /app/
#WORKDIR /app
#ENTRYPOINT ["java", "-jar", "hrindex-0.0.1-SNAPSHOT.jar"]