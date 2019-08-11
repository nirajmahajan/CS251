#!/bin/bash
chmod 755 $1/*
a=$1/*.txt
for var in $a
do
	out=$( awk 'BEGIN{FS=":"}NR==4{print $2}' $var | sed 's/ //g' )
	cp -t ./$out $var >& /dev/null
	if [ $? -eq 1 ]
	then
		mkdir -p ./$out
		cp $var ./$out
	fi
done
chmod -R 755 *
