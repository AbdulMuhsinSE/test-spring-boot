FROM openjdk:17-alpine
LABEL maintainer="abdulmuhsin.alkandari@gmail.com"
VOLUME /main-app
ADD target/discounts-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]