#!/bin/bash
# 1: container

printf "\n ---------------- Kill test Database container ---------------- \n\n"

if [ "$(docker ps -q -f name=$1)" ]; then
	docker rm -vf $1
fi
