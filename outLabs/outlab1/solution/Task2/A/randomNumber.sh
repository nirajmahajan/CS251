#!/bin/bash

digits=$1

num_digits () {
	count=0
	f=$1
	while [ $f != 0 ]
	do
		((count++))
		f=$(( f/$2 ))
	done
	return $count
}

reducer () {
	e=$1
	num_digits $e 10
	dig=$?
	while [ $dig -gt $2 ]
	do
		e=$(( e/10 ))
		dig=$(( dig-1 ))
	done
	echo $e
	return $e
}

generate(){
	local rand=$( od -vAn -N4 -tu4 < /dev/urandom )
	local digitsrt=$1
	local ans
	if [ $digitsrt -le 8 ]
	then
		ans=$(reducer $rand $digitsrt)
	else
		ans=$(reducer $rand 8)
		digitsrt=$(( digitsrt-8))
		digitsrt=$(generate $digitsrt)
		ans="$ans$digitsrt"
	fi
	echo $ans	
}

generate $1
