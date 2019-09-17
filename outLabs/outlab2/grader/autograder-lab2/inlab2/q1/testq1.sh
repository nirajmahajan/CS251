#!/bin/bash
vis_testcases=($(seq 1 1 3))
script_name="$1"
tail_inp="_inp_vis_testcase"
tail_out="_out_vis_testcase"
file_dir="./testcasesq1/vis/"
out_dir="./output/vis/"
out_ref="./testcasesq1/vis/"
marks=0;
feedback="Task1: \n"

feedback="$feedback visible testcases : (testcase:marks)\n"
[[ -d $out_dir ]] || mkdir -p "$out_dir"
if [[ $script_name ]];
then
	# echo ""
	for i in "${vis_testcases[@]}"
	do
		# echo "Current testcase -> $file_dir$i$tail_inp"
		# echo "Your Output file -> $out_dir$i$tail_out"
		# echo "Sample Output file -> $out_ref$i$tail_out"
		sed -f $script_name $file_dir$i$tail_inp > $out_dir$i$tail_out
		diff $out_ref$i$tail_out $out_dir$i$tail_out > result

		if [[ -s result ]];
		then
			# echo "status -> failed"
			feedback="$feedback $i:0\t"
		else
			# echo "status -> passed"
			feedback="$feedback $i:1\t"
			# $feedback="$feedback+$i":1\t"
			marks=$(($marks+1));
		fi
		rm result
		# echo ""
	done
	# echo $marks
	# echo -e $feedback
	rm -r $out_dir
else
	# echo "please create a script with name q1.sed"
	# echo $marks
	feedback="$feedback No file or file name is wrong \n"
	# echo $feedback
fi


hid_testcases=($(seq 1 1 7))
script_name="$1"
tail_inp="_inp_hid_testcase"
tail_out="_out_hid_testcase"
file_dir="./testcasesq1/hid/"
out_dir="./output/hid/"
out_ref="./testcasesq1/hid/"
# marks=0;
# feedback="Task1: \n"

feedback="$feedback \n hidden testcases : (testcase:marks)\n"
[[ -d $out_dir ]] || mkdir -p "$out_dir"
if [[ $script_name ]];
then
	# echo ""
	for i in "${hid_testcases[@]}"
	do
		# echo "Current testcase -> $file_dir$i$tail_inp"
		# echo "Your Output file -> $out_dir$i$tail_out"
		# echo "Sample Output file -> $out_ref$i$tail_out"
		sed -f $script_name $file_dir$i$tail_inp > $out_dir$i$tail_out
		diff $out_ref$i$tail_out $out_dir$i$tail_out > result

		if [[ -s result ]];
		then
			# echo "status -> failed"
			feedback="$feedback $i:0\t"
		else
			# echo "status -> passed"
			feedback="$feedback $i:1\t"
			# $feedback="$feedback+$i":1\t"
			marks=$(($marks+1));
		fi
		rm result
		# echo ""
	done
	echo $marks,$feedback
	rm -r $out_dir
else
	# echo "please create a script with name q1.sed"
	feedback="$feedback No file or file name is wrong \n"
	echo  $marks,$feedback
fi

