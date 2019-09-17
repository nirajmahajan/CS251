#!/bin/bash

timeout() {

    time=$1

    # start the command in a subshell to avoid problem with pipes
    # (spawn accepts one command)
    command="/bin/sh -c \"$2\""

    expect -c "set echo \"-noecho\"; set timeout $time; spawn -noecho $command; expect timeout { exit 1 } eof { exit 0 }"    

    if [ $? = 1 ] ; then
        # echo "Timeout after ${time} seconds"
        echo '0,Task3: Timeout'

    fi

}


[ $# -eq 0 ] && { echo "Usage: $0 <teamname>"; exit 1; }

submission_dir="./submissions/"
temp_dir="./tempq3"
teamname="$2"
roll_no="$1"
Prefix="outlab2-"
echo -n $roll_no
echo -n ","
echo -n $teamname
echo -n ","
[[ -d $temp_dir ]] || mkdir -p "$temp_dir/$teamname/"
var="find $submission_dir -name \"*$teamname\.*\""
path="$(eval $var)"
echo -n "$path"
echo -n ","
tar -xzf "$path" -C "$temp_dir/$teamname/" >/dev/null 2>/dev/null
if [ $? -eq 0 ];
    then
    :
    else
    tar -xf "$path" -C "$temp_dir/$teamname/" > /dev/null 2>/dev/null
    if [ $? -eq 0 ];
    then
    :
    else
        unzip "$path" -d "$temp_dir/$teamname/" > /dev/null 2>/dev/null
    fi
fi

var="find \"$temp_dir/$teamname\" -iname \"viewWithoutColor.awk\""
loc_of_files="$(eval $var)"

timeout 15 "./testq3a.sh '$loc_of_files'"
var="find \"$temp_dir/$teamname\" -iname \"viewWithColor.sh\""
loc_of_files="$(eval $var)"
chmod 755 "$loc_of_files"
timeout 15 "./testq3b.sh '$loc_of_files'"
var="find \"$temp_dir/$teamname\" -iname \"viewSemester.sh\""
loc_of_files="$(eval $var)"
chmod 755 "$loc_of_files"
timeout 15 "./testq3c.sh '$loc_of_files'"
var="find \"$temp_dir/$teamname\" -iname \"viewCourse.sh\""
loc_of_files="$(eval $var)"
chmod 755 "$loc_of_files"
timeout 15 "./testq3d.sh '$loc_of_files'"
var="find \"$temp_dir/$teamname\" -iname \"calculateCPI.sh\""
loc_of_files="$(eval $var)"
chmod 755 "$loc_of_files"
timeout 15 "./testq3e.sh '$loc_of_files'"
var="find \"$temp_dir/$teamname\" -iname \"calculateSPI.sh\""
loc_of_files="$(eval $var)"
chmod 755 "$loc_of_files"
timeout 15 "./testq3f.sh '$loc_of_files'"

rm -r $temp_dir
