#!/bin/bash
./gradlew build
cd api
ls build/libs
cf push

