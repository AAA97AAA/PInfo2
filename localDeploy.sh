#!/bin/bash

# Set max JVM heap size for maven
MAVEN_OPTS='-Xmx512m'

# Set all variables
ACADEMI_CO_NETWORK='academi_co_network'

IMAGE_DB='academi_co_mysql'
IMAGE_APPSERVER='academi_co_wildfly'
IMAGE_APPSERVER_IT='academi_co_wildfly_tests'

DOCKER_DB='concealed_cader' # 172.18.0.4
# DOCKER_DB_IT='shady_selimi' # 172.18.0.3
DOCKER_APPSERVER='cheeky_binko' # 172.18.0.2
DOCKER_APPSERVER_IT='bodacious_goren'

DOCKER_DEPLOY='/tmp/docker-deploy'

# Scripts: script.sh network image container [...]



printf "\n ---------------- Create /tmp/docker-deploy directory ---------------- \n\n"

if [ ! -d /tmp/docker-deploy ]; then
  mkdir /tmp/docker-deploy
fi

printf "\n ---------------- Launch network ---------------- \n\n"

if [ !"$(docker network ls -q -f name=$ACADEMI_CO_NETWORK)" ]; then
     docker network create --subnet 172.18.0.0/16 $ACADEMI_CO_NETWORK
fi

# Launch the containers
if [[ -n $1 ]]; then
   if [[ $1 != "--skipTests" ]]; then
       ./Jenkins/Scripts/AppserverIntegrationTests/runAppServerTests.sh \
           $IMAGE_APPSERVER_IT $DOCKER_APPSERVER_IT
   fi
else
    ./Jenkins/Scripts/AppserverIntegrationTests/runAppServerTests.sh \
        $IMAGE_APPSERVER_IT $DOCKER_APPSERVER_IT
fi
./Jenkins/Scripts/Database/runDatabase.sh \
    $ACADEMI_CO_NETWORK $IMAGE_DB $DOCKER_DB

if [[ -n $1 ]]; then
   if [[ $1 == "--skipTests" ]]; then
      # Wait for database to be running
      IT_DB_BUILDING=false
      while ! docker exec -it $DOCKER_DB mysql -u root -padmin -e "USE ACADEMI_CO_DB" ; do
         echo "Waiting for database..."
         IT_DB_BUILDING=true
         sleep 5
      done
      if [[ $IT_DB_BUILDING == true ]]; then
         echo "Building tables..."
         sleep 40
      fi
   fi
fi;

./Jenkins/Scripts/Appserver/runAppServer.sh \
    $ACADEMI_CO_NETWORK $IMAGE_APPSERVER $DOCKER_APPSERVER $DOCKER_DB

printf "\n ---------------- Build Maven project ---------------- \n\n"

mvn -B -DskipTests -f academi-co/pom.xml clean package

printf "\n ---------------- Launch Tests ---------------- \n\n"

if [[ -n $1 ]]; then
   if [[ $1 != "--skipTests" ]]; then
       mvn -f academi-co/pom.xml test
   fi
else
    mvn -f academi-co/pom.xml test
fi

# Kill all test containers if required
if [[ -n $1 ]]; then
   if [[ $1 != "--no-kill" && $1 != "--skipTests" ]]; then
      ./Jenkins/Scripts/AppserverIntegrationTests/killAppServerTests.sh $DOCKER_APPSERVER_IT
   fi
else
   ./Jenkins/Scripts/AppserverIntegrationTests/killAppServerTests.sh $DOCKER_APPSERVER_IT
fi

# Deploy the project
Jenkins/Scripts/deploy.sh $DOCKER_DEPLOY
