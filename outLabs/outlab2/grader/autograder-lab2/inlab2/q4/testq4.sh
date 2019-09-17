#!/bin/bash
vis_testcases=($(seq 1 1 2))
subparts=("D" "C" "B" "A")
tail_inp="_inp_vis_testcase"
tail_out="_out_vis_testcase"
file_dir="./testcasesq4/vis/"
script_name="$1"
out_dir="./output/vis/"
res_prefix="./q4_sol_res.sh."
out_ref="./testcasesq4/vis/"
marks=0;
feedback="Task4: \n"

feedback="$feedback visible testcases: (testcase:PART:marks)\n"
[[ -d $out_dir ]] || mkdir -p "$out_dir"
if [[ -s $script_name ]];
then
	# echo ""
	for i in "${vis_testcases[@]}"
	do
		# echo "Current testcase -> $file_dir$i$tail_inp"
		$script_name $file_dir$i$tail_inp > "$out_dir$i$tail_out" 2>/dev/null
		# echo "Your Output file -> $out_dir$i$tail_out"
		for j in "${subparts[@]}"
	   	do
		#    echo "Part .$j"
		#    echo "Sample Output file -> $out_ref$i$tail_out.$j"
			$res_prefix$j "$out_dir$i$tail_out" > "$out_dir$i$tail_out$j"
		   	diff $out_ref$i$tail_out $out_dir$i$tail_out$j > result
			if [[ -s result ]];
			then
				case $j in
				"A") 
				feedback="$feedback $i::0\t";;
				esac
				# echo "status -> failed";
			else
				# echo "status -> passed"
				case $j in
				"D") marks=$(awk "BEGIN {print $marks+6.5;exit;}")
				feedback="$feedback $i:$j:6.5\t";;
				"C") marks=$(awk "BEGIN {print $marks+4;exit;}")
				feedback="$feedback $i:$j:4\t";;
				"B") marks=$(awk "BEGIN {print $marks+2;exit;}")
				feedback="$feedback $i:$j:2\t";;
				"A") marks=$(awk "BEGIN {print $marks+0.5;exit;}")
				feedback="$feedback $i:$j:0.5\t";;
				esac
				rm result
				break;
			fi
			rm result
		
		#    echo ""
		done	
		# echo ""
	done
	
	# echo $marks,$feedback
	# rm -r $out_dir
else
	# echo "q4.sh missing"
	feedback="$feedback No file or file name is wrong \n"
	# echo  $marks,$feedback
fi

hid_testcases=($(seq 1 1 8))
subparts=("D" "C" "B" "A")
tail_inp="_inp_hid_testcase"
tail_out="_out_hid_testcase"
file_dir="./testcasesq4/hid/"
script_name="$1"
out_dir="./output/hid/"
res_prefix="./q4_sol_res.sh."
out_ref="./testcasesq4/hid/"
# marks=0;
# feedback="Task4: \n"

feedback="$feedback hidden testcases: (testcase:PART:marks)\n"
[[ -d $out_dir ]] || mkdir -p "$out_dir"
if [[ -s $script_name ]];
then
	# echo ""
	for i in "${hid_testcases[@]}"
	do
		# echo "Current testcase -> $file_dir$i$tail_inp"
		# echo "Your Output file -> $out_dir$i$tail_out"
		# echo "$script_name $file_dir$i$tail_inp > $out_dir$i$tail_out"
		$script_name $file_dir$i$tail_inp > "$out_dir$i$tail_out" 2>/dev/null
		for j in "${subparts[@]}"
	   	do
		#    echo "Part .$j"
		#    echo "Sample Output file -> $out_ref$i$tail_out.$j"
			# echo "$res_prefix$j $out_dir$i$tail_out >$out_dir$i$tail_out$j"
			$res_prefix$j "$out_dir$i$tail_out" > "$out_dir$i$tail_out$j"
		   	diff $out_ref$i$tail_out $out_dir$i$tail_out$j > result
			if [[ -s result ]];
			then
				case $j in
				"A") 
				feedback="$feedback $i::0\t";;
				esac
				# echo "status -> failed";
			else
				# echo "status -> passed $j"
				case $j in
				"D") marks=$(awk "BEGIN {print $marks+6.5;exit;}")
				feedback="$feedback $i:$j:6.5\t";;
				"C") marks=$(awk "BEGIN {print $marks+4;exit;}")
				feedback="$feedback $i:$j:4\t";;
				"B") marks=$(awk "BEGIN {print $marks+2;exit;}")
				feedback="$feedback $i:$j:2\t";;
				"A") marks=$(awk "BEGIN {print $marks+0.5;exit;}")
				feedback="$feedback $i:$j:0.5\t";;
				esac
				rm result
				break;
			fi
			rm result
		
		#    echo ""
		done	
		# echo ""
	done
	
	echo $marks,$feedback
	# rm -r $out_dir
else
	# echo "q4.sh missing"
	feedback="$feedback No file or file name is wrong \n"
	echo  $marks,$feedback
fi

rm -r $out_dir