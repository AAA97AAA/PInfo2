#!/bin/bash
# 1: network, 2: image, 3: container

if [ !"$(docker images -q $2)" ]; then
 	docker build --rm -t $2 `dirname "$0"`
fi

if [ "$(docker ps -q -f name=$3)" ]; then
	docker rm -vf $3
fi

docker run -p 3306:3306 --name=$3 -e MYSQL_ROOT_PASSWORD=admin -d $2
