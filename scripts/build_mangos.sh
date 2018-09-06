#!/usr/bin/env bash

set -ev

VERSION=$(git tag --sort version:refname | tail -1)

docker login -u ${USER} -p ${PASSWORD}
rm -rf mangos-docker
git clone https://github.com/StephenSorriaux/mangos-docker.git

cd mangos-docker/${PROJECT}/mangosd

docker build -t ssorriaux/${PROJECT}-server:${VERSION} --build-arg MANGOS_SERVER_VERSION=${VERSION} .
docker tag ssorriaux/${PROJECT}-server:${VERSION} ssorriaux/${PROJECT}-server:latest
docker push ssorriaux/${PROJECT}-server:${VERSION}
docker push ssorriaux/${PROJECT}-server:latest
