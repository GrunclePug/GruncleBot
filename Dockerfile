FROM openjdk:12-alpine

COPY build/libs/GruncleBot-1.0-all.jar /grunclebot.jar

CMD ["java", "-jar", "grunclebot.jar"]
