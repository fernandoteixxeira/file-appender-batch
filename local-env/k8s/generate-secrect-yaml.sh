#!/bin/bash

k3d cluster create creator

kubectl apply -f ./01.namespace.yaml
kubectl create secret generic envs -n file-appender --from-env-file=../batch.env
kubectl get secret envs -n file-appender -o yaml | grep -v "creationTimestamp:" | grep -v "resourceVersion:" | grep -v "uid:" > ./02.secret.yaml

k3d cluster rm creator
