#!/bin/bash

printf "\n ---------------- Kill AppServer container ---------------- \n\n"

if [ "$(docker ps -q -f name=$1)" ]; then
	docker rm -vf $1
fi
