#!/bin/bash

if [ ! $# -eq 2 ]; then
	echo "Usage:  ./downloadContents.sh <url> <directory_path>"
	exit 1
fi

DOMAIN="$(echo ${url#*//})"
DOMAIN="$(echo ${DOMAIN%%/*})"

wget -qr -np -p --convert-links -l100000 -P"$2" --domains "$DOMAIN" "$1"

tree -J "$2" > "urlReport.json"

md5sum "urlReport.json"

COUNT=$(grep -c "{" "urlReport.json")
echo "$COUNT"

ANS=$(ps -q "$COUNT" -o comm=)

if [ "$?" -eq 1 ]; then
	echo "No such process"
else
	echo "$ANS"
fi