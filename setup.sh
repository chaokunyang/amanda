#!/usr/bin/env bash
nohup java -jar build/libs/amanda-0.1.0.jar --spring.profiles.active=init --server.port=80 --amanda.username=amanda --amanda.password=timeyang --amanda.name=amanda &