#!/bin/bash
# 1: network, 2: image, 3: container

printf "\n ---------------- Create test Database image ---------------- \n\n"

if [[ "$(docker images -q $2 2> /dev/null)" == "" ]]; then
 	docker build --rm -t $2 `dirname "$0"`
fi

printf "\n ---------------- Kill test Database container ---------------- \n\n"

if [ "$(docker ps -q -f name=$3)" ]; then
	docker rm -vf $3
fi

printf "\n ---------------- Launch test Database container ---------------- \n\n"

docker run --ip="172.18.0.3" --net=$1 -p 13306:3306 --name=$3 -e MYSQL_ROOT_PASSWORD=admin -d $2
