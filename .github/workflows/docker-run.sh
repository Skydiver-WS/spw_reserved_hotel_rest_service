#!/bin/bash

# Достаём версию из pom.xml
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

echo "Using version: $VERSION"

# Останавливаем и удаляем старый контейнер
docker rm -f hotel_resr_service || true

# Запускаем новый контейнер
docker run -d -p 8443:8443 --name bokking-db-app skydiverkhv/hotel_rest_service:"$VERSION"

echo "Container db_service started with version $VERSION"