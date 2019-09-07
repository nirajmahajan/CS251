#! /bin/bash

cd master/
for dir in * ; do
    name=$(echo $dir | rev | cut -d '-' -f 2- | rev | sed 's/-/\t/g')
    cp -r $dir/Task"$1"/ ../autograder/P"$1"/ 2>/dev/null
    cd ../autograder/P"$1"/
    printf "$name\t"
    ./p"$1"_grader.sh
    cd ../../master/
done
cd ../