#!/bin/bash
sed '/^ *$/d' $1 | 
sed 's/^-*-$/\#/' | 
tr -cd '\11\12\15\40-\176' | 
sed '1d' | 
awk 'BEGIN{RS="\n#\n";FS=":|\n"} NF>4 {print > $4".txt"}' 
chmod 755 *
