#!/bin/bash

CONTAINER_NAME='bodacious_goren'
IMAGE_NAME='academi-co-test-wildfly'

if [[ "$(docker images -q $IMAGE_NAME 2> /dev/null)" == "" ]]; then
 	docker build --rm -t $IMAGE_NAME `dirname "$0"`
fi

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
	docker rm -vf $CONTAINER_NAME
fi

docker run -p 8080:8080 -p 9990:9990 -p 8777:8787 --name=$CONTAINER_NAME -d $IMAGE_NAME
