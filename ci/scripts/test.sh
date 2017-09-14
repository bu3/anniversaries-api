#!/bin/bash

set -e -x

pwd
ls -al

cd anniversaries-api
./gradlew test