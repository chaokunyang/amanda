#!/usr/bin/env bash
cat application.pid | xargs kill -term
nohup java -jar build/libs/amanda-0.1.0.jar --spring.profiles.active=init --server.port=80 --amanda.username=xxx --amanda.password=xxx --amanda.name=xxx &