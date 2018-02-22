#!/bin/bash
if [ ! -d build/Client.class ]; then
	./build.sh
fi
cd build
java Client
