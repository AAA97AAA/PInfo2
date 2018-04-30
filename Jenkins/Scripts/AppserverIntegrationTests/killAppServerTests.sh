#!/bin/bash

CONTAINER_NAME='bodacious_goren'

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
	docker rm -vf $CONTAINER_NAME
fi

if [ "$(docker images -q $IMAGE_NAME)" ]; then
 	docker rmi -f $IMAGE_NAME
fi
