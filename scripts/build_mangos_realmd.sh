#!/usr/bin/env bash

set -ev

VERSION=$(git tag --sort version:refname | tail -1)

docker login -u ${USER} -p ${PASSWORD}
rm -rf mangos-docker
git clone https://github.com/StephenSorriaux/mangos-docker.git

cd mangos-docker/${PROJECT}/realmd

docker build -t ssorriaux/${PROJECT}-realmd:${VERSION} --build-arg MANGOS_SERVER_VERSION=${VERSION} .
docker tag ssorriaux/${PROJECT}-realmd:${VERSION} ssorriaux/${PROJECT}-realmd:latest
docker push ssorriaux/${PROJECT}-realmd:${VERSION}
docker push ssorriaux/${PROJECT}-realmd:latest
docker rmi ssorriaux/${PROJECT}-realmd:latest ssorriaux/${PROJECT}-realmd:${VERSION}