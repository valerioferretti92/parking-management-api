#!/bin/bash

TEMP=$(kubectl get namespace | grep "pma" -o)
if [ -z "$TEMP" ]
then echo "nothing to do..."
else 
  kubectl delete all --all --namespace pma
  kubectl delete namespace pma
fi
