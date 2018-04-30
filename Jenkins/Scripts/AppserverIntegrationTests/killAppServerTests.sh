#!/bin/bash

CONTAINER_NAME='bodacious_goren'
IMAGE_NAME='academi-co-test-wildfly'

if [ "$(docker images -q $IMAGE_NAME)" ]; then
 	docker rmi -f $IMAGE_NAME
fi
