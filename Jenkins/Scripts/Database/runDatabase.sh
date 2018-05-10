#!/bin/bash

CONTAINER_NAME='concealed_cader'
IMAGE_NAME='academi-co-mysql'
NETWORK_NAME='academi-co-network'

if [ !"$(docker network ls -q -f name=$NETWORK_NAME)" ]; then
     docker network create --subnet 172.18.0.0/16 $NETWORK_NAME
fi

if [[ "$(docker images -q $IMAGE_NAME 2> /dev/null)" == "" ]]; then
 	docker build --rm -t $IMAGE_NAME `dirname "$0"`
fi

if [ !"$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
	docker run --ip="172.18.0.3" --net=$NETWORK_NAME -p 13306:3306 --name=$CONTAINER_NAME -e MYSQL_ROOT_PASSWORD=admin -d $IMAGE_NAME
fi
