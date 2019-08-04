#!/bin/bash

re='^[0-9]+$'
if ! [[ $1 =~ $re ]] 
then
	echo "Invalid argument!" >&2 
	exit 1
fi
> numberOfPrimeYears.txt
prime_check () {
	if [ $1 -le 1 ]
	then
		return 0
	fi
	b=$1
	i=2	
	e=$(( i * i ))
	while [ $e -le $b ]
	do
		c=$(( b % i ))
		if [ $c == 0 ]
		then
			return 0
		fi
		(( i++ ))
		e=$(( i * i ))
	done
	return 1
}

counter () {
	ig=2
	count=0
	f=$1
	while [ $ig -le $f ]
	do
		prime_check $ig
		ch=$?
		if [ $ch == 1 ]
		then
			(( count++ ))
		fi
		(( ig++ )) 
	done
	return $count
}

prime_check $1
out=$?
cou=$(counter $1)
echo $cou | cat > numberOfPrimeYears.txt

if [ $out == 1 ]
then
	echo "Prime Year!"
else 
	echo "Not a prime year."
fi


