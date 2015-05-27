#!/bin/bash

mkdir ~/home_device

cp -rf ./ ~/home_device/src

mkdir ~/home_device/user
mkdir ~/home_device/user/flows
mkdir ~/home_device/user/temp

cd ~/home_device/src/
make
