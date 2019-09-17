#!/bin/bash
out_ref="./testcases/"
script_name="$1"
name_of_File="viewWithoutColor"
out_dir="./output/"
marks=0;
[[ -d $out_dir ]] || mkdir -p "$out_dir"
if [[ -s "$script_name" ]];
then
	awk -f "$script_name" ./resources/allCoursesTaken.csv > "$out_dir$name_of_File.out"
	diff "$out_dir$name_of_File.out" "$out_ref$name_of_File.out" >result
	if [[ -s result ]];
	then
	:
	else
		marks=$(($marks+10));
	fi
	# rm result
	echo -n $marks
	echo -n ","
	
else
	echo -n $marks
	echo -n ","
fi
