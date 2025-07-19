#Start with base image of java
FROM openjdk:17-jdk-slim

#Information of the docker container
LABEL MAINTAINER=iAtulB

#Copy the jar file
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#Run the jar file
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]