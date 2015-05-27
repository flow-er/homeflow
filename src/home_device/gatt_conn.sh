#!/bin/bash
# sh gatt_conn 1 2

#arguments
addr=$1
cmd=$2

if [ $cmd != "3" ]; then
	#get handle
#	handle=\
#	"$(gatttool -b $addr --characteristics |\
#	 grep 0000a002-0000-1000-8000-00805f9b34fb |\
#	 cut -d ',' -f 3 |\
#	 awk '{ print $5 }')"

	echo $handle

	#send command to ble device
	suc="$(gatttool -b $addr --char-write-req -a 0x0010 -n $cmd |\
	       awk '{ print $5 }')"

	echo $suc

	#case of connection failed
	if [ $suc != "successfully" ]; then
		exit -1
	fi

	exit 1
else
	#get return value from ble device
	hex=0x"$(gatttool -b $addr --char-read -a 0x0008 |\
	         cut -d ' ' -f 3)"

	ret="$(printf "%d" $((hex)))"

	echo $ret

	#exit with return value
	exit ret
fi
