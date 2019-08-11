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

for (( i = 1; i <= NRnow; i++ )); do

	if [ $i -le 3 ]; then
		awk -v iter="$i" '{if (NR == iter) {print;}}' $1
		continue
	fi

	CURR_FONT=$(awk -v iter="$i" 'BEGIN{FS="\n"}{if(NR == iter) {print;}}' font.out)
	CURR_BACk=$(awk -v iter="$i" 'BEGIN{FS="\n"}{if(NR == iter) {print;}}' back.out)


	NewFont="${!CURR_FONT}"
	NewBack="${!CURR_BACk}"


	awk -v toprint="$i" -v Font="$NewFont" -v Back="$NewBack" '
	BEGIN {
	} 
	{
		if (NR == toprint && toprint <= 3) {
			print;
		} else if (NR == toprint) {
			printf "" Font;
			printf "" Back;
			printf $0;
			printf("'$RESET_ALL'")
			printf "\n"
		}
	}
	END {
	}
	' $1
done

rm back.out
# rm font.out
