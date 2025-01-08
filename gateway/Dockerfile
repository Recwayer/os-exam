FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY build/libs/gateway.jar /app/app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
