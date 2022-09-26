FROM openjdk:18.0.2 AS server

WORKDIR /app
COPY ./target/jar/spring-boot-docker.jar ./spring-boot-docker.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "spring-boot-docker.jar"]
