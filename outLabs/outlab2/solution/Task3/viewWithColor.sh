#!/bin/bash

# source ./resources/defineColors.sh ########################################

BLACK_FONT=`tput setaf 0`
RED_FONT=`tput setaf 1`
GREEN_FONT=`tput setaf 2`
YELLOW_FONT=`tput setaf 3`
BLUE_FONT=`tput setaf 4`
MAGENTA_FONT=`tput setaf 5`
CYAN_FONT=`tput setaf 6`
WHITE_FONT=`tput setaf 7`

BLACK_BACKGROUND=`tput setab 0`
RED_BACKGROUND=`tput setab 1`
GREEN_BACKGROUND=`tput setab 2`
YELLOW_BACKGROUND=`tput setab 3`
BLUE_BACKGROUND=`tput setab 4`
MAGENTA_BACKGROUND=`tput setab 5`
CYAN_BACKGROUND=`tput setab 6`
WHITE_BACKGROUND=`tput setab 7`

RESET_ALL=`tput sgr0`


rm back.out
rm font.out

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

	CURR_FONT=$(awk -v iter="$i" 'BEGIN{FS="\n"}{if(NR == iter) {print;}}' font.out)
	CURR_BACk=$(awk -v iter="$i" 'BEGIN{FS="\n"}{if(NR == iter) {print;}}' back.out)

	NewFont=${CURR_FONT}
	NewBack=${CURR_BACk}
	NewReset=${RESET_ALL}


	awk -v toprint="$i" -v Font=$NewFont -v Back=$NewBack -v Reset=$NewReset '
	BEGIN {
	} 
	{
		if (NR == toprint && toprint <= 3) {
			print;
		} else if (NR == toprint) {
			printf "" Font;
			printf "" Back;
			# printf("'$Font'");
			# printf("'$Back'");
			print;
		}
	}
	END {
		printf Reset;
	}
	' $1
done