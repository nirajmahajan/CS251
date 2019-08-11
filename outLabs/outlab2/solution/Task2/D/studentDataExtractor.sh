#!/bin/bash
sed '/^$/d' $1 | 
tr -dc '\0-\177' | 
awk 'BEGIN{RS="-";FS=":|\n"} NF>4 {print > $5".txt"}' 
chmod 755 *

#sed '/^ *$/d' $1 | 
#sed 's/^-*-$/\#/' | 
#tr -dc '\0-\177' | 
#sed '1d' | 
#awk 'BEGIN{RS="\n#\n";FS=":|\n"} NF>4 {print > $4".txt"}' 
#chmod 755 *
