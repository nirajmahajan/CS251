#!/bin/bash

awk '
BEGIN {
	FS="[ ][ ]*"
	RS="\n"
	OFS=","
	ORS="\n"
}
{
	if (NR<4) {
		print;
	}
}' $1

VALID=$(cat $1 | sed -e 's/\n/\r\n$/g')
VALID=$(echo "$VALID" | sed -e '/^--*.*$/d')
VALID=$(echo "$VALID" | sed -e 's/\(^.*\)\([a-zA-Z][a-zA-Z]\)\([ ]\)\([0-9][0-9][0-9]\)\(.*$\)/\2\4 \1\2\3\4\5/g')
VALID=$(echo "$VALID" | sed -e '/^  *.*$/d' | sort -k1)
VALID=$(echo "$VALID" | sed -e 's/^[a-zA-Z][a-zA-Z][0-9][0-9][0-9] //')

VALID=$(echo "$VALID" | awk -v year="$3" -v sem="$2" '
BEGIN {
	FS=" "
	RS="\n"
}
{
	doNotPrint=0;
	for ( i = 0; i < NF; i++ ) {
		if($i == year) {
			doNotPrint++;
		}
		if($i == sem) {
			doNotPrint++;
		}
	}

	if(doNotPrint == 2) {
		print;
	}
}')



echo "$VALID"
