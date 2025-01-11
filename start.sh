#!/bin/bash
git pull origin master
docker-compose stop
docker-compose up --build -d