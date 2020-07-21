#!/usr/bin/env bash
DOCKER_REPO=docker.repo.zhongwang-group.cn:32038/repository/zhongwang-private-docker-hosted
APP_NAME=lem-server
APP_VERSION=latest
echo "pull代码"
git pull

echo "打包"
source /etc/profile

docker login --username=zhangxinyang --password=zhangxinyang123456 $DOCKER_REPO
mvn package
docker build -t $DOCKER_REPO/$APP_NAME:$APP_VERSION .
docker push $DOCKER_REPO/$APP_NAME:$APP_VERSION
