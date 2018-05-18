#!/bin/bash
# 1: docker-deploy path

printf "\n ---------------- Deploy .war files ---------------- \n\n"

if [[ -e $1/academi-co.war ]]; then
    rm -f $1/academi-co.war
fi
if [[ -e $1/academi-co.war.failed ]]; then
    rm -f $1/academi-co.war.failed
fi
if [[ -e $1/academi-co.war.deployed ]]; then
    rm -f $1/academi-co.war.deployed
fi

if [[ -e academi-co/target/academi-co.war ]]; then
    cp academi-co/target/academi-co.war $1
fi

IS_DEPLOYED=false
while ! IS_DEPLOYED ; do
   sleep 5
   if [[ -e $1/academi-co.war.deployed ]]; then
       printf "Deployment sucessful\n"
       IS_DEPLOYED=true
   elif [[ -e $1/academi-co.war.failed ]]; then
       printf "Deployment failed\n"
       IS_DEPLOYED=true
   fi
done
