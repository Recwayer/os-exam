FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY build/libs/domain-service.jar /app/app.jar

EXPOSE 50051
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
