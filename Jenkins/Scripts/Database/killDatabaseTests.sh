#!/bin/bash

CONTAINER_NAME='shady_selimi'

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
	docker rm -vf $CONTAINER_NAME
fi
