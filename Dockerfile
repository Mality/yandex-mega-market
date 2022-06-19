FROM maven:3.8.6-jdk-11 AS builder

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean package -DskipTests

# Second stage: minimal runtime environment
FROM openjdk:11-jre-slim

# copy jar from the first stage
COPY --from=builder target/mega-market-0.0.1-SNAPSHOT.jar mega-market-0.0.1-SNAPSHOT.jar

EXPOSE 80

CMD ["java", "-jar", "mega-market-0.0.1-SNAPSHOT.jar"]


# FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
# ADD . /src
# WORKDIR /src
# RUN ./mvnw package -DskipTests
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","target/mega-market-0.0.1-SNAPSHOT.jar"]

# FROM adoptopenjdk:11-jre-hotspot
# COPY target/*.jar app.jar
# ENTRYPOINT [ "java", "-jar", "/app.jar" ]
