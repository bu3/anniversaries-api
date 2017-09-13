#!/bin/bash

set -e -x

pwd
ls -al

cd anniversaries-api
./gradlew test build

echo '>>>>>>>>>>>>> Copying jar file'
cp  -R ./build ../artifact/
echo '>>>>>>>>>>>>> Copying CF Manifest'
cp manifest.yml ../artifact