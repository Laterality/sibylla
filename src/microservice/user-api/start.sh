#!/bin/sh
chmod +x ./gradlew
./gradlew build && java -jar ./build/libs/user-api-0.0.1-SNAPSHOT.jar