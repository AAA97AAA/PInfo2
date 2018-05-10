#!/bin/bash

DOCKER_DEPLOY='/tmp/server/tmp/docker-deploy'

if [[ -e $DOCKER_DEPLOY/academi-co.war ]]; then
    rm -f $DOCKER_DEPLOY/academi-co.war
fi
if [[ -e $DOCKER_DEPLOY/academi-co.war.deployed ]]; then
    rm -f $DOCKER_DEPLOY/academi-co.war.deployed
fi

ls -R academi-co/target/

if [[ -e academi-co/target/academi-co.war ]]; then
    cp academi-co/target/academi-co.war $DOCKER_DEPLOY
fi
