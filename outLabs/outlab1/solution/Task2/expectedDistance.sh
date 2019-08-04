#!/bin/bash

attempts=$1
i=0
sum=0
while [ $i -lt $attempts ]
do
	./findTheAnswer.sh
	a=$(wc -l howFarFromTruth.txt | awk '{ print $1 }')
	sum=$(( sum+a ))
	((i++))
done
sum=$(( sum/attempts ))
echo $sum
