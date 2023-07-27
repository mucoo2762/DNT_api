FROM ubuntu:18.04

RUN apt-get -q update
RUN apt-get install -q -y software-properties-common wget curl

### jdk 11
RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt-get update
RUN apt install -y openjdk-11-jdk

### MariaDB
RUN apt-get install -y mariadb-server

ENTRYPOINT ["sh", "./start.sh"]

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} dnt-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/dnt-api-0.0.1-SNAPSHOT.jar"]
