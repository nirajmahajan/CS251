BEGIN {
	FS=",";
	RS="\r\n";
	NAME_COL=0;
}

{
	if(NR==1){
		for(i=0;i<(20*(NF-1));i++){printf "-"}
		printf "\n";
		for(i=1;i<=NF;i++){
			if(!($i == "Name")) {
				printf("%20s",$i);
			} else {
				NAME_COL=i;
			}

		}
		printf "\n";
		for(i=0;i<(20*(NF-1));i++){printf "-"}
		printf "\n";
	} else {
		for(i=1;i<=NF;i++){
			if(!(i == NAME_COL)) {
				printf("%20s",$i);
			}
		}
		printf "\n";
	}
}

END{
}