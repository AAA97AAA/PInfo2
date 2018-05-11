#!/bin/bash
# 1: network, 2: image, 3: container, 4: db

if [[ "$(docker images -q $2 2> /dev/null)" == "" ]]; then
 	docker build --rm -t $2 `dirname "$0"`
fi

if [ !"$(docker ps -q -f name=$3)" ]; then
	docker run --ip="172.18.0.1" --net=$1 --link $4:db -p 8080:8080 -p 9990:9990 -p 8787:8787 -v "/tmp/docker-deploy:/opt/jboss/wildfly/standalone/deployments/:rw" --name=$3 -d $2
fi
