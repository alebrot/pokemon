#!/bin/bash
# exit when any command fails
set -e

./gradlew clean bootJar
docker build -t khlebtsov/pokemon:latest .
docker run -p 8080:8080 khlebtsov/pokemon:latest
#docker push khlebtsov/pokemon:latest
