#!/bin/bash
# exit when any command fails
set -e

./gradlew clean build
docker build -t khlebtsov/pokemon:latest .
docker push khlebtsov/pokemon:latest
