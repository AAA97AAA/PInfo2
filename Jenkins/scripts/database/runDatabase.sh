#!/bin/bash

CONTAINER_NAME='concealed_cader'
IMAGE_NAME='academi-co-mysql'

if [[ "$(docker images -q $IMAGE_NAME 2> /dev/null)" == "" ]]; then
 	docker build -t $IMAGE_NAME .
fi

docker run -p 13306:3306 --name=$CONTAINER_NAME -e MYSQL_ROOT_PASSWORD=admin -d $IMAGE_NAME
