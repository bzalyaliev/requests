#!/usr/bin/env bash
#from frontend dir
#build frontend code to have it in ./frontend/build
cd frontend
yarn build
cd ..
#fron root dir
rm -rf ./target/classes/static
cp -R ./frontend/build/ ./target/classes/static