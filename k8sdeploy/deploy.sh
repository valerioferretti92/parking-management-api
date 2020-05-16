#!/bin/bash

# Prerequisites: kubectl installed and configured to manage a valid Kubernetes cluster.
# The deployment has been tested on a Minikube cluster.

TEMP=$(kubectl get namespace | grep "pma" -o)
if [ -z "$TEMP" ]
then kubectl create namespace pma
else echo "namespace/pma unchanged"
fi
kubectl apply -f mongo-statefulset.yaml --namespace pma
kubectl apply -f parking-management-api.yaml --namespace pma 
