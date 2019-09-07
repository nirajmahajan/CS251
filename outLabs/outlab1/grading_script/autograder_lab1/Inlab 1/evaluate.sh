#! /bin/bash

cd master/
for dir in * ; do
    name=$(echo $dir | cut -d '-' -f 1)
    cp $dir ../autograder/ 2>/dev/null
    cd ../autograder/
    printf "$name\t"
    ./grader.sh
    cd ../master/
done
cd ../