FROM openjdk:17-jdk

ARG JAR_FILE=/build/libs/Eattoday-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /Eattoday.jar

ENTRYPOINT ["java","-jar", "/Eattoday.jar"]