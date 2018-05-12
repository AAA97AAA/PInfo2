#!/bin/bash
# 1: network, 2: image, 3: container, 4: db

printf "\n ---------------- Create AppServer image ---------------- \n\n"

if [[ "$(docker images -q $2 2> /dev/null)" == "" ]]; then
 	docker build --rm -t $2 `dirname "$0"`
fi

printf "\n ---------------- Launch AppServer container ---------------- \n\n"

if [[ $(docker ps -f "name=$3" --format '{{.Names}}') != $3 ]]; then
	docker run --ip="172.18.0.2" --net=$1 --link $4:db -p 8080:8080 -p 9990:9990 -p 8787:8787 -v "/tmp/docker-deploy:/opt/jboss/wildfly/standalone/deployments/:rw" --name=$3 -d $2
fi
