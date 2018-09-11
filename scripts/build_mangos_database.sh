#!/usr/bin/env bash

set -ev

VERSION="Rel21"

docker login -u ${USER} -p ${PASSWORD}
rm -rf mangos-docker
git clone https://github.com/StephenSorriaux/mangos-docker.git

cd mangos-docker/${PROJECT}/mysql-database

docker build -t ssorriaux/${PROJECT}-database-mysql:${VERSION} .
docker tag ssorriaux/${PROJECT}-database-mysql:${VERSION} ssorriaux/${PROJECT}-database-mysql:latest
docker push ssorriaux/${PROJECT}-database-mysql:${VERSION}
docker push ssorriaux/${PROJECT}-database-mysql:latest
