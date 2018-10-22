FROM openjdk:8
WORKDIR /usr/src/app
ADD . /usr/src/app
RUN chmod +x gradlew
RUN ./gradlew
CMD java -jar /usr/src/app/build/libs/auth-api-0.0.1-SNAPSHOT.jar
EXPOSE 8080