#!/bin/bash

docker tag "file-appender-batch" "registry.local:5050/file-appender-batch"
docker push "registry.local:5050/file-appender-batch"
