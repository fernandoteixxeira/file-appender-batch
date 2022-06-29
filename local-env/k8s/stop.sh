#!/bin/bash

kubectl delete -f ./

k3d cluster rm local
