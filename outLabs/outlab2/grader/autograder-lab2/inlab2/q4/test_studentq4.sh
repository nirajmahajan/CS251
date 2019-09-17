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
loc_of_files=$(find $temp_dir/$Student_rollno -iname "Task4")
list_of_files="$(ls $loc_of_files)"
loc=$(find $temp_dir/$Student_rollno -iname "Task4")
if [ -z $loc ];
then
:
else
    cp -R "$loc/." .
fi
chmod 777 $list_of_files
timeout 36000 ./testq4.sh ./q4.sh
if [ $? -ne 0 ];
then
echo '0, Task4: Timeout'
else
:
fi
if [ -z $loc_of_files ];
then
:
else
    rm $list_of_files
fi

rm -r $temp_dir
