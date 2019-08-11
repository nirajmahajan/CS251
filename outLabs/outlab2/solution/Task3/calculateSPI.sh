#!/bin/bash

awk -v sem=$3 -v year=$4 '
BEGIN {
	FS=",";
	RS="\r\n";
	CRED_Sum=0;
	WEI_Sum=0;
}
{
	if(NR == FNR) {
		gradeToScore[$1]=$2;
		next;
	} else if (($1 == year)&&($2 == sem)){
		CRED_Sum += $5;
		WEI_Sum += ($5 * gradeToScore[$7]);
	}
} 
END {
	if (CRED_Sum == 0) {
		print "No credits taken this semester";
		exit 1;
	}
	printf("%.4f\n",(WEI_Sum / CRED_Sum));
}
' $2 $1
