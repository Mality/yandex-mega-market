FROM maven:3.8.6-jdk-11 AS builder

ADD ./pom.xml pom.xml
ADD ./src src/

RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim

COPY --from=builder target/*.jar *.jar

EXPOSE 8080

CMD ["java", "-jar", "*.jar"]
