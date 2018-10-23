#!/bin/sh
chmod +x ./gradlew
./gradlew build && java -jar ./build/libs/article-api-0.0.1-SNAPSHOT.jar