#!/bin/bash
# 1: image, 2: container

printf "\n ---------------- Create AppServerIntegrationTests image ---------------- \n\n"

if [[ "$(docker images -q $1 2> /dev/null)" == "" ]]; then
 	docker build --rm -t $1 `dirname "$0"`
fi

printf "\n ---------------- Kill AppServerIntegrationTests container ---------------- \n\n"

if [[ "$(docker ps -f "name=$2" --format '{{.Names}}')" != "" ]]; then
	docker rm -vf $2
fi

printf "\n ---------------- Launch AppServerIntegrationTests container ---------------- \n\n"

docker run -p 18080:8080 -p 19990:9990 -p 18777:8787 --name=$2 -d $1
