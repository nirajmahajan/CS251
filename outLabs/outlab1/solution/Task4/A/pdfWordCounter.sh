#!/bin/bash

if [ ! $# -eq 2 ]; then
	echo "Usage: ./pdfWordCounter.sh <URL> <Word>"
	exit 1
fi

wget -O "$$temp.pdf" -q "$1"

pdftotext "$$temp.pdf" - | grep -iwo $2 | wc -l

rm "$$temp.pdf"
