#!/bin/bash

if [ "$(docker ps -q -f name=$1)" ]; then
	docker rm -vf $1
fi
