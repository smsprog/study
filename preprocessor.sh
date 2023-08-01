#!/bin/bash
echo 'Hi, preprocessor is running....'

grep -i interface ./*/*.java | awk -F":" '{print $1}' | sort -r | while read fl ; do
	echo $fl
	cat $fl
	ifname=$(cat $fl | grep -E -v "//|package |import |^$" | tr -d "\n" | sed -r 's/[;{}]/\n/g' | grep -i interface | sed -r 's/.+interface (.+)/\1/')
	
	#echo $ifname
	cat $fl | grep -E -v "//|package |import |^$" | tr -d "\n" | sed -r 's/[;{}]/\n/g' | grep "(" | grep ")" | sed -r 's/([^ ]+) (.+[^(])\(.+/\1 \2/' | while read rtn mtd ; do
		echo $rtn, $mtd
	done
	
	break
done