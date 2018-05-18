#!/bin/bash

printf "\n ---------------- Kill AppServerIntegrationTests container ---------------- \n\n"

if [[ "$(docker ps -f "name=$1" --format '{{.Names}}')" != "" ]]; then
	docker rm -vf $1
fi
