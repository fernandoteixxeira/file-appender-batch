#!/bin/bash

docker build -t file-appender-batch -f ../Dockerfile ../
docker tag "file-appender-batch" "fernandotaa/file-appender-batch:2"
docker push "fernandotaa/file-appender-batch:2"