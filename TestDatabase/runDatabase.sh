#!/bin/bash

if [[ "$(docker images -q testdbcontainer 2> /dev/null)" == "" ]]; then
 	docker build -t testdbcontainer `dirname "$0"`
fi
docker run -p 13306:3306 -e MYSQL_ROOT_PASSWORD=admin -d testdbcontainer
