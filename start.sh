#!/bin/bash

#docker stop dnt-db
docker run --name dnt-db -p 3305:3306 --network dnt-net -e MYSQL_ROOT_PASSWORD=10041004 -e MYSQL_DATABASE=dnt -d mariadb:10.5.13
