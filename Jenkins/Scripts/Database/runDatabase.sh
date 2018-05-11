#!/bin/bash
# 1: network, 2: image, 3: container

if [[ "$(docker images -q $2 2> /dev/null)" == "" ]]; then
 	docker build --rm -t $2 `dirname "$0"`
fi

if [ !"$(docker ps -q -f name=$3)" ]; then
	docker run --ip="172.18.0.1" --net=$1 -p 3306:3306 --name=$3 -e MYSQL_ROOT_PASSWORD=admin -d $2
fi
