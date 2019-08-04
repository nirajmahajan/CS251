#!/bin/bash

attempts=$1
i=0
sum=0
while [ $i -lt $attempts ]
do
	./findTheAnswer.sh
	a=$(< howFarFromTruth.txt wc -l)
	sum=$(( sum+a ))
	((i++))
done
sum=$(( sum/attempts ))
echo $sum

/bin/rm howFarFromTruth.txt