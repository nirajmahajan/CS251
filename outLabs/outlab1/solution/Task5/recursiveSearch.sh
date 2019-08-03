#!/bin/bash

if [ $# -eq 0 ]; then
	echo "Usage: ./recursiveSearch.sh <words-to-search>"
	exit 1
fi

OUTPUT=$(grep -nRiw $1 Data)

for WORD in "$@" ; do
	OUTPUT=$(echo -e "$OUTPUT" | grep -iw $WORD)
done

echo "$OUTPUT"
