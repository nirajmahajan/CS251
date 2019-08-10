#!/bin/bash
sed -e '/-/d' $1 | 
sed -e 's/^$/\@/g' |
sed -e 's/.*:/%/' | 
awk 1 ORS=' ' | 
awk 'BEGIN{FS=" %"; RS="@"; OFS="|"}
	 {print $2,$3,$4,$5,$6}' |
sed -e '/||||/d' | 
sed -e '1i\
Student Name|Roll Number|CPI|Department|Courses Undertaken
' | 
cat > studentData.csv
chmod 755 *