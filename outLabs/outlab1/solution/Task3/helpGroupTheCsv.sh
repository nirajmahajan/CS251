#!/bin/bash

> $2
a1=$(cut -d ',' -f 1,2,3 $1)
a2=$(cut -d ',' -f 1,4,5 $1)
a3=$(cut -d ',' -f 1,6,7 $1)
echo "$a1" | cat >> $2
echo "$a2" | cat >> $2
echo "$a3" | cat >> $2
a5=$(sort -t',' -k3 $2)
echo "$a5" | cat >$2
