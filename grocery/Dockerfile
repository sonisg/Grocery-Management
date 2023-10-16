FROM openjdk:8-jre-alpine

COPY target/grocery.jar .


EXPOSE 8100


ENTRYPOINT ["java", "-jar", "grocery.jar"]