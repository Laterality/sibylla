#!/bin/sh
chmod +x ./gradlew
./gradlew build && java -jar ./build/libs/auth-api-0.0.1-SNAPSHOT.jar