#!/bin/bash
# sh gatt_conn 1 2

#arguments
addr=$1
cmd=$2

if [ $cmd != "3" ]; then
	#send command to ble device
	suc="$(gatttool -b $addr --char-write-req -a 0x0010 -n 0$cmd |\
	       awk '{ print $5 }')"

	#case of connection failed
	if [ $suc != "successfully" ]; then
		exit -1
	fi

	#exit with successful connection status
	exit 1
else
	#get return value from ble device
	hex=0x"$(gatttool -b $addr --char-read -a 0x000e |\
	         cut -d ' ' -f 3)"

	ret="$(printf "%d" $((hex)))"

	#exit with return value
	exit ret
fi
