#!/bin/bash

CONTAINER_NAME='bodacious_goren'

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
	docker rm -vf $CONTAINER_NAME
fi
