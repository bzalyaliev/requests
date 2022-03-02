FROM openjdk:11

COPY target/material-requests-0.0.1-SNAPSHOT.jar material-requests-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/material-requests-0.0.1-SNAPSHOT.jar"]


