#!/bin/bash

fly -t lite set-pipeline -p anniversaries -c pipeline.yml \
    --var "cf-username=$(echo $CF_USERNAME)" \
    --var "cf-password=$(echo $CF_PASSWORD)" \
    --var "cf-organisation=$(echo $CF_ORGANISATION)" \
    --var "cf-space=$(echo $CF_SPACE)"