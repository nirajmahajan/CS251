#!/bin/bash
wget -O "tempabc.pdf" -q "$1"
chmod 755 *
pdftotext "tempabc.pdf"
sed -e 's/\f//g' tempabc.txt |
awk '
/^Courses Undertaken:/{ORS=" "}
/^$/{printf "\n"}
!NF{ORS="\n"};
1;            
END{ORS=""; print RS }' | 
sed 's/ -/\n-/g; 2d' |
tr -dc '\0-\177' |
cat > studentData.txt
chmod 755 *
rm "tempabc.pdf"
rm "tempabc.txt"
