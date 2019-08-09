#!/bin/bash
read ans
key=$1
for i in {A..Z}
do
	if [ $key == $i ]
	then
		break
	else
		ans=$(echo $ans | tr A-Z B-ZA)
	fi
done	
echo $ans