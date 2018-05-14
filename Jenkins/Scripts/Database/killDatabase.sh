#!/bin/bash

CONTAINER_NAME='concealed_cader'

printf "\n ---------------- Kill Database container ---------------- \n\n"

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
	docker rm -vf $CONTAINER_NAME
fi
