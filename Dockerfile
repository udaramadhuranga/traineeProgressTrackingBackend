FROM openjdk:17
ADD target/trainee-backend.jar app.jar
ENTRYPOINT ["java", "-jar","app.jar"]
EXPOSE 3005