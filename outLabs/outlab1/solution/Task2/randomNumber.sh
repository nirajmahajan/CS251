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

rand=$( od -vAn -N4 -tu4 < /dev/urandom )
outp=$(reducer $rand $digits)
echo $outp