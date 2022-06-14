FROM adoptopenjdk:11-jre-hotspot

ARG JAP_FILE = *.jar

COPY @{JAP_FILE} application.jar

ENTRYPOINT [ "java", "-jar", "application.jar" ]
