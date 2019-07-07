FROM openjdk:8
ADD target/mastermind.jar mastermind.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","mastermind.jar"]

