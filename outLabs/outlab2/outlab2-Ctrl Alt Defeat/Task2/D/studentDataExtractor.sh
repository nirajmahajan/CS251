#!/bin/bash
sed 's/^-\+//' $1| 
sed '1d' |
sed '1d' | 
tr -dc '\0-\177' | 
awk 'BEGIN{RS="\n\n";FS=":|\n"; ORS=""} {print > $4".txt"}' 
chmod 755 *
