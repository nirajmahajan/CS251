#!/bin/bash
awk 'BEGIN{FS=OFS="|"} NR==1{print} NR>1{print $1,$2,$3,$4,$5 | "sort -t\| -Vrk 3"}' $1 |
cat > sortedStudentData.csv
chmod 755 *
cut -d"|" -f1 sortedStudentData.csv |
sed -e '1d' | 
awk 'NR<=5{print}' | 
cat > top5Students.txt
chmod 755 *
