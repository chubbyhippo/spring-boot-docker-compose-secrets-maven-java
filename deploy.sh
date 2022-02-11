#!/usr/bin/env sh

docker swarm init
printf -n "password"|docker secret create custom-secret -
docker stack deploy --compose-file docker-compose.yml demo

