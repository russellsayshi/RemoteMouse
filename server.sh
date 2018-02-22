#!/bin/bash
if [ ! -d build/Server.class ]; then
	./build.sh
fi
cd build
java Server
