#!/bin/bash

CONTAINER_NAME='shady_selimi'
IMAGE_NAME='academi-co-mysql'

if [[ "$(docker images -q $IMAGE_NAME 2> /dev/null)" == "" ]]; then
 	docker build -t $IMAGE_NAME `dirname "$0"`
fi

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
	docker rm -vf $CONTAINER_NAME
fi

docker run -p 3306:3306 --name=$CONTAINER_NAME -e MYSQL_ROOT_PASSWORD=admin -d $IMAGE_NAME
