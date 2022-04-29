FROM openjdk:12-alpine

ADD *.json src/main/resources/

COPY build/libs/GruncleBot-1.0-all.jar /demo.jar

CMD ["java", "-jar", "demo.jar"]
