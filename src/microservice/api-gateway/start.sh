#!/bin/sh
chmod +x ./gradlew
./gradlew build && java -jar ./build/libs/api-gateway-0.0.1-SNAPSHOT.jar