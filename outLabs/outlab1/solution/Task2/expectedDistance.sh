#!/bin/bash

attempts=$1
i=0
sum=0
while [ $i -lt $attempts ]
do
	./findTheAnswer.sh
	a=$(cat howFarFromTruth.txt)
	sum=$(( sum+a ))
	((i++))
done
sum=$(( sum/attempts ))
echo $sum