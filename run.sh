#!/usr/bin/env bash

source etc/setenv.sh

function composeUp() {
    ./mvnw docker-compose:up
}

function composeDown() {
    ./mvnw docker-compose:down
}

function runHerokuLocal() {
    export JAVA_OPTS=$JAVA_OPTS_RUN_DEFAULT
    showSettings
    composeUp
    ./mvnw clean package
    heroku local web
    composeDown
}

function testApp() {
    export JAVA_OPTS=$JAVA_OPTS_RUN_DEFAULT
    showSettings
    ./mvnw clean package bootJar
    composeUp
    ./mvnw install test check
    composeDown
}

function run() {
    export JAVA_OPTS=$JAVA_OPTS_RUN_DEFAULT
    showSettings
    composeUp
    ./mvnw -e clean spring-boot:run
    composeDown
}

function testAppDev() {
    export JAVA_OPTS=$JAVA_OPTS_RUN_DEFAULT
    showSettings
    ./mvnw clean package bootJar
    ./mvnw install test check
}

function runDev() {
    export JAVA_OPTS=$JAVA_OPTS_RUN_DEFAULT
    showSettings
    ./mvnw -e -DskipTests=true clean dependency:tree package spring-boot:run
}

function firstSetup() {
    #./mvnw clean dependency:tree dependency:resolve dependency:resolve-plugins dependency:sources install -DskipTests=true
    ./mvnw clean package site -DskipTests=true
}
function setupTravis() {
    ./mvnw clean
    ./mvnw dependency:purge-local-repository
    ./mvnw install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
}
function main() {
    ## runHerokuLocal
    ## composeDown
    ## composeUp
    ## run
    ## testApp
    #runDev
    ##testAppDev
    #firstSetup
    setupTravis
}

main
