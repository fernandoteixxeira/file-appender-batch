#!/bin/bash

./generate-secrect-yaml.sh
./build-docker-images.sh

k3d cluster create local --registry-create registry.local:registry.local:5050
./push-images-to-local-registry.sh


kubectl apply -f ./