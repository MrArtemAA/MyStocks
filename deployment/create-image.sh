#!/bin/bash

cp ../build/distributions/uberJar/stocks.jar .
docker build -t stocks .