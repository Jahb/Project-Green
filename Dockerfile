FROM openjdk:8-alpine

COPY "server/build/libs" "/app"

WORKDIR "/app"

EXPOSE 8080 8080

CMD ["java", "-jar", "server-1.0.jar"]