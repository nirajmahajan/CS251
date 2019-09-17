#!/bin/bash
[ $# -eq 0 ] && { echo "Usage: $0 <rollno>"; exit 1; }

submission_dir="./submissions"
temp_dir="./tempq4"
Student_rollno="$1"
Prefix="inlab*"
echo -n $Student_rollno
echo -n ","
[[ -d $temp_dir ]] || mkdir -p "$temp_dir/$Student_rollno/"

tar -xzf $submission_dir"/"$Prefix$Student_rollno* -C $temp_dir/$Student_rollno/ 2> /dev/null
if [ $? -eq 0 ];
    then
    :
    else
    tar -xf $submission_dir"/"$Prefix$Student_rollno* -C $temp_dir/$Student_rollno/ > /dev/null 2> /dev/null
    if [ $? -eq 0 ];
    then
    :
    else
        unzip $submission_dir"/"$Prefix$Student_rollno* -d $temp_dir/$Student_rollno/> /dev/null 2> /dev/null
    fi
fi
loc_of_files=$(find $temp_dir/$Student_rollno -iname "q1.sed")
timeout 36000 ./testq1.sh $loc_of_files
rm -r $temp_dir
