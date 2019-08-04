#!/bin/bash

if [ ! $# -eq 2 ]; then
	echo "Usage:  ./downloadContents.sh <url> <directory_path>"
	exit 1
fi

CURR_PATH=$(pwd)

cd "$2"

DOMAIN="$(echo ${url#*//})"
DOMAIN="$(echo ${DOMAIN%%/*})"

wget -qr -np --domains "$DOMAIN" "$1"

cd "$CURR_PATH"

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