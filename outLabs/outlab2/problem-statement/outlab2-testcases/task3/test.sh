out_dir="./output/"
res_dir="./resources/"
test_dir="./testcases/"
status(){
	if [[ -s result ]];
	then
		echo "status -> failed";
	else
		echo "status -> passed"
	fi
	rm result

	echo ""

}
[[ -d $out_dir ]] || mkdir -p "$out_dir"
awk -f viewWithoutColor.awk $res_dir"allCoursesTaken.csv" > $out_dir"viewWithoutColor.out"
echo "Task A"
diff $out_dir"viewWithoutColor.out" $test_dir"viewWithoutColor.out" > result
status
./viewWithColor.sh $out_dir"viewWithoutColor.out" $res_dir"creditsRequirements.csv" > $out_dir"viewWithColor.out"
echo "Task B"
diff $out_dir"viewWithColor.out" $test_dir"viewWithColor.out" > result
status
./viewSemester.sh $out_dir"viewWithoutColor.out" Autumn 2018 > $out_dir"viewSemester.out"
echo "Task C"
diff $out_dir"viewSemester.out" $test_dir"viewSemester.out" > result
status
./viewCourse.sh $out_dir"viewWithoutColor.out" "CS 1" > $out_dir"viewCourse.out"
echo "Task D"
diff $out_dir"viewCourse.out" $test_dir"viewCourse.out" > result
status
./calculateCPI.sh $res_dir"allCoursesTaken.csv" $res_dir"letterGradeToNumber.csv" > $out_dir"calculateCPI.out"
echo "Task E"
diff $out_dir"calculateCPI.out" $test_dir"calculateCPI.out" > result
status
./calculateSPI.sh $res_dir"allCoursesTaken.csv" $res_dir"letterGradeToNumber.csv" Autumn 2016 > $out_dir"calculateSPI.out"
echo "Task F"
diff $out_dir"calculateSPI.out" $test_dir"calculateSPI.out" > result
status