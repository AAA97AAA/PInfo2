#!/bin/bash

# Set all variables
ACADEMI_CO_NETWORK='academi_co_network'

IMAGE_DB='academi_co_mysql'
IMAGE_APPSERVER='academi_co_wildfly'
IMAGE_APPSERVER_IT='academi_co_wildfly_tests'

DOCKER_DB='concealed_cader' # 172.18.0.1
# DOCKER_DB_IT='shady_selimi' # 172.18.0.3
DOCKER_APPSERVER='cheeky_binko' # 172.18.0.2
DOCKER_APPSERVER_IT='bodacious_goren'

# Scripts: script.sh network image container [...]



printf "\n ---------------- Kill all containers ---------------- \n\n"

docker rm -vf $DOCKER_APPSERVER_IT
docker rm -vf $DOCKER_DB
docker rm -vf $DOCKER_APPSERVER

printf "\n ---------------- Kill network ---------------- \n\n"

docker network rm $ACADEMI_CO_NETWORK

printf "\n ---------------- Delete all image ---------------- \n\n"

docker rmi -f $IMAGE_APPSERVER_IT
docker rmi -f $IMAGE_DB
docker rmi -f $IMAGE_APPSERVER

printf "\n ---------------- Delete all .war files ---------------- \n\n"

if [[ -e /tmp/docker-deploy/academi-co.war ]]; then
    rm -f /tmp/docker-deploy/academi-co.war
fi
if [[ -e /tmp/docker-deploy/academi-co.war.failed ]]; then
    rm -f /tmp/docker-deploy/academi-co.war.failed
fi
if [[ -e /tmp/docker-deploy/academi-co.war.undeployed ]]; then
    rm -f /tmp/docker-deploy/academi-co.war.undeployed
fi
if [[ -e /tmp/docker-deploy/academi-co.war.deployed ]]; then
    rm -f /tmp/docker-deploy/academi-co.war.deployed
fi
