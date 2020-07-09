FROM java:8
EXPOSE 8081
ADD /target/bookstore-service.jar bookstore-service.jar
ENTRYPOINT ["java","-jar","bookstore-service.jar"]