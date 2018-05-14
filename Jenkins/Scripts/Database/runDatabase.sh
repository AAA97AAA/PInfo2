#!/bin/bash
# 1: network, 2: image, 3: container

printf "\n ---------------- Create Database image ---------------- \n\n"

if [[ "$(docker images -q $2 2> /dev/null)" == "" ]]; then
 	docker build --rm -t $2 `dirname "$0"`
fi

printf "\n ---------------- Launch Database container ---------------- \n\n"

if [[ "$(docker ps -f "name=$3" --format '{{.Names}}')" == "" ]]; then
	docker run --ip="172.18.0.4" --net=$1 -p 3306:3306 --name=$3 -e MYSQL_ROOT_PASSWORD=admin -d $2
fi
