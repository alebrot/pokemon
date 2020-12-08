#!/bin/bash
# exit when any command fails
set -e

docker pull khlebtsov/pokemon:latest
docker run -p 5000:5000 khlebtsov/pokemon:latest
