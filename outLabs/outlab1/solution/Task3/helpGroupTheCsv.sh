#!/bin/bash

bew=$(awk -v FS='[,]' '{print $3,$1,$2"\n"$5,$1,$4"\n"$7,$1,$6}' OFS=, $1)
dew=$(echo "$bew" | sort)
cew=$(echo "$dew" | awk -v FS='[,]' '{print $2,$3,$1}' OFS=,)
echo "$cew" | cat > $2