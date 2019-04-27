#!/bin/sh
docker build . -t my-api-app
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run --network host my-api-app"
