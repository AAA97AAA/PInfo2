#!/bin/bash

CONTAINER_NAME='cheeky_binko'
DB_NAME='concealed_cader'
IMAGE_NAME='academi-co-wildfly'
NETWORK_NAME='academi-co-network'

if [ !"$(docker network ls -q -f name=$NETWORK_NAME)" ]; then
     docker network create --subnet 172.18.0.0/16 $NETWORK_NAME
fi

if [[ "$(docker images -q $IMAGE_NAME 2> /dev/null)" == "" ]]; then
 	docker build --rm -t $IMAGE_NAME `dirname "$0"`
fi

if [ !"$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
	docker run --ip="172.18.0.3" --net=$NETWORK_NAME --link $DB_NAME:db -p 18080:8080 -p 19990:9990 -p 18787:8787 -v "/tmp/docker-deploy:/opt/jboss/wildfly/standalone/deployments/:rw" --name=$CONTAINER_NAME -d $IMAGE_NAME
fi
