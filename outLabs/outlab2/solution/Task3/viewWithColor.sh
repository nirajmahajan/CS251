#!/bin/bash

source ./resources/defineColors.sh 

if [ -f "back.out" ]; then
	rm back.out
fi
if [ -f "font.out" ]; then
	rm font.out
fi

NRnow=$( awk '
BEGIN {
	TagIndex = 0;
	FS=","
	RS="\n"
}
{
	if (NR == FNR){
		if (NR == 1) {
			next;
		}
		font[$1] = $3 "_FONT" ;
		back[$1] = $4 "_BACKGROUND";
		next;
	} else {
		FS=" ";
		RS="\n"
		if(FNR == 2) {
			for(i=1;i<=NF;i++) {
				if($i == "Tag") {
					TagIndex = i;
				}
			}
		}
		if (FNR <= 3) {
			system("echo \" \" >> back.out");
			system("echo \" \" >> font.out");
			next;
		}else {
			if (NF == 8) {
				Tag = $6 " " $7;
			} else if (NF == 7) {
				Tag = $6;
			}
			system("echo \""back[Tag]"\" >> back.out");
			system("echo \""font[Tag]"\" >> font.out");
		}
	}
}
END {
	print FNR;
}' $2 $1)

var=$(awk 'BEGIN{} {} END{print NR}' ./resources/defineColors.sh)
awk 'NR<4{print $0}' $1
awk -v l1="$NRnow" -v l2="$var" -v res="$RESET_ALL" 'BEGIN{FS="[=,]"}
{if(NR<=l2){a[$1]=$2}
else if (NR<=l2+l1){font[FNR]=$1}
else if (NR<=l2+2*l1){back[FNR]=$1}
else {if(FNR>3){b=a[back[FNR]]; f=a[font[FNR]]; b=substr(b,2,length(b)-2); f=substr(f,2,length(f)-2);
	system(b); system(f); printf $0; printf res; printf "\n";}}}' ./resources/defineColors.sh font.out back.out $1
