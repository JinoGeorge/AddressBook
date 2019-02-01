# our base build image
FROM maven:3.6.0-jdk-8-alpine as maven

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src/main ./src/main

# build for release
RUN mvn package -DskipTests

# our final base image
FROM openjdk:8-jre-alpine

# set deployment directory
WORKDIR /app

# copy over the built artifact from the maven image
COPY --from=maven target/*.jar ./app.jar

# expose the application port
EXPOSE 9090

# set the startup command to run your binary
ENTRYPOINT ["java", "-jar", "./app.jar"]
