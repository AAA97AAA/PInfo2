#!/bin/bash

CONTAINER_NAME='cheeky_binko'

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
	docker rm -vf $CONTAINER_NAME
fi
