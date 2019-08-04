#!/bin/bash

if [ ! $# -eq 2 ]; then
	echo "Usage: ./pdfWordCounter.sh <URL> <Word>"
	exit 1
fi

wget -O "$$temp.pdf" -q "$1"

pdftotext "$$temp.pdf" - | grep -wci "$2"

rm "$$temp.pdf"