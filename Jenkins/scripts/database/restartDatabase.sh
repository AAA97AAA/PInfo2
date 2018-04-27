#!/bin/bash

CONTAINER_NAME='concealed_cader'
IMAGE_NAME='academi-co-mysql'

docker rm -vf $CONTAINER_NAME

docker run -p 13306:3306 --name=$CONTAINER_NAME -e MYSQL_ROOT_PASSWORD=admin -d $IMAGE_NAME
