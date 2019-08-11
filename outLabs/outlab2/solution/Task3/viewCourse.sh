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
VALID=$(echo "$VALID" | sed -e 's/\(^.*\)\([a-zA-Z][a-zA-Z]\)\([ ]\)\([0-9][0-9][0-9]\)\(.*$\)/\2\3\4 \1\2\3\4\5/g')
VALID=$(echo "$VALID" | sed -e '/^  *.*$/d' | sort -k1)

VALID=$(echo "$VALID" | awk -v code="$2" '
BEGIN {
	FS=" "
	RS="\n"
}
{
	course = $1 " " $2;
	if(course ~ code) {
		print;
	}
}')

VALID=$(echo "$VALID" | sed -e 's/^[a-zA-Z][a-zA-Z][ ][0-9][0-9][0-9] //')

echo "$VALID"
