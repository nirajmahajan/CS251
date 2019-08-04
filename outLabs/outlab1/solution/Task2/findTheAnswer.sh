#!/bin/bash


> howFarFromTruth.txt
a=$(bash ./randomNumber.sh 2)
count=1
while [ $a -ne 42 ]
do
	echo $a | cat >> howFarFromTruth.txt
	a=$(bash ./randomNumber.sh 2)
	((count++))
done

echo $a | cat >> howFarFromTruth.txt
