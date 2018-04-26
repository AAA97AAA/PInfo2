#!/bin/bash

CONTAINER_NAME='concealed_cader'

docker network inspect jee7-network 1>/dev/null 2>/dev/null

if [ $? -ne 0 ]; then
	docker network create --subnet 172.18.0.0/16 academi-co-network
fi

if [[ "$(docker images -q academi-co-mysql 2> /dev/null)" == "" ]]; then
 	docker build -t academi-co-mysql ./Jenkins/scripts/database/
fi

if [[ docker inspect -f {{.State.Running}} $CONTAINER_NAME ]]; then
    docker rm -fv $CONTAINER_NAME
fi

docker run --ip="172.18.0.3" --net="academi-co-network" -p 13306:3306 --name=$CONTAINER_NAME -e MYSQL_ROOT_PASSWORD=admin -d academi-co-mysql
