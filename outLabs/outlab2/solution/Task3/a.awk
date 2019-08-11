BEGIN {
	FS=","
	RS="\r\n"
	OFS=","
	ORS="\r\n"
	FORE_POS=0;
	BACK_POS=0;
	TAG_POS=0;
}
{
	if (NR==1) {
		for (i=1;i<=NF;i++){
			if ($i == "color_BACKGROUND") {
				BACK_POS=i;
			}
			if($i == "tag") {
				TAG_POS=i;
			}
			if ($i == "color_FONT") {
				FORE_POS=i;
			}
		}
	} else {
		print $TAG_POS, $FORE_POS, $BACK_POS;
	}
}
