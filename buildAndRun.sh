#!/bin/bash
# exit when any command fails
set -e

./gradlew clean build
docker build -t khlebtsov/pokemon:latest .
docker run -p 5000:5000 khlebtsov/pokemon:latest
