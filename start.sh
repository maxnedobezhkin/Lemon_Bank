#!/bin/bash
git pull origin develop
docker-compose stop
docker-compose up --build -d