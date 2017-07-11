#!/usr/bin/env bash
cat application.pid | xargs kill -term
nohup java -jar build/libs/amanda-0.1.0.jar --server.port=80 &