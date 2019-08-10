#!/bin/bash
 # sed -e '/^[^R].*/d' $1 | 
 # sed -e 's/.*://' | 
 # sed -e '/^[R].*/d' |
 # sed -e '/^$/d' |
 # tr -cd '\11\12\15\40-\176' | 
 # awk 1 ORS='\n\n\n' |  
 # cat > RollNoList
 # sed -e 's/-.*//g; 1d;' $1 |
 # sed -e '1d' |  
 # cat > ActualData
 # awk  'BEGIN{RS="\n\n\n"}
 # 	 {if(NR==FNR)
 # 	 	a[FNR] = $1
	#  else
 # 	 	print > a[FNR]".txt"}' RollNoList ActualData
 # chmod 755 *
 # rm RollNoList
 # rm ActualData

 sed '/^ *$/d' $1 | 
 sed 's/^-*-$/\#/' | 
 tr -cd '\11\12\15\40-\176' | 
 #cat > 1
 awk 'BEGIN{RS="#\n";FS=":|\n"} /^$/ {next} NF>4 {print > $4".txt"}' 