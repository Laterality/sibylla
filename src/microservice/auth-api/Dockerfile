FROM openjdk:8
COPY ./build/lib/auth-api-0.0.1-SNAPSHOT.jar /usr/src/app/
RUN java -jar /usr/src/app/auth-api-0.0.1-SNAPSHOT.jar
EXPOSE 8080