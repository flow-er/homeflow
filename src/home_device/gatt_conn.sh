#!/bin/bash
# sh gatt_conn 1 2

#arguments
addr=$1
cmd=$2

#get handle
handle=\
"$(gatttool -b $addr --characteristics |\
 grep 0000a002-0000-1000-8000-00805f9b34fb |\
 cut -d ',' -f 3 |\
 awk '{ print $5 }')"

echo $handle

#send command to ble device
suc=\
"$(gatttool -b $addr --char-write-req -a $handle -n $cmd |\
 awk '{ print $5 }')"

echo $suc

#case of connection failed
if [ $suc != "successfully" ]; then
	exit -1
fi

#get return value from ble device
ret=\
"$(gatttool -b $addr --char-read -a $handle |\
 cut -d ':' -f 2)"

echo $ret

#exit with return value
exit ret
