#! /bin/bash

# Task 4A
POINTS_A=0

test_words=(i door asked WE HolMeS)
rm -f -r test_4A/
mkdir test_4A

for i in {0..4}
do
	cd test_4A
	cp ../Task4/A/pdfWordCounter.sh . >/dev/null 2>/dev/null
	chmod 777 pdfWordCounter.sh >/dev/null 2>/dev/null
	timeout 10 ./pdfWordCounter.sh https://www.cse.iitb.ac.in/~saitejat/cs251_test.pdf ${test_words["$i"]} 2>/dev/null > myout"$i".txt
	diff -bBw myout"$i".txt ../truth/A/truth"$i".txt >/dev/null 2>/dev/null
	[ $? -eq 0 ] && let POINTS_A++
	rm -f myout"$i".txt
	count="$(ls | wc -l)"
	[ $count -eq 1 ] && let POINTS_A++
	rm -f * >/dev/null 2>/dev/null
	cd ../
done

printf "$POINTS_A\t"
rm -f -r test_4A/

# TASK 4B
mkdir outdir
cp Task4/B/* outdir/ 2>/dev/null

# wget --recursive --page-requisites --no-parent --convert-links <url> gives the required html page

POINTS_B=0

cd outdir
test1=www.cse.iitb.ac.in/~saksham.goel/
d1=dir1
ans1_1=../truth/B/ans1-1.txt
ans1_2=../truth/B/ans1-2.txt
ans1_3=../truth/B/ans1-3.txt

test2=www.cse.iitb.ac.in/~yashsharma/
d2=dir2
ans2_1=../truth/B/ans2-1.txt
ans2_2=../truth/B/ans2-2.txt
ans2_3=../truth/B/ans2-3.txt

chmod 777 downloadContents.sh 2>/dev/null

timeout 20 ./downloadContents.sh $test1 $d1 >out1.txt 2>/dev/null

rm "$d1/${test1}index.html" 2>/dev/null # Did this because there was a view count on this page

diff -bBw -r ../truth/B/dir1/ dir1/ >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_B++
[ -s urlReport.json ]
[ $? -eq 0 ] && POINTS_B=$( calc $POINTS_B + 0.4 )

head -n 1 out1.txt >o 2>/dev/null
diff -bBw $ans1_1 o >/dev/null 2>/dev/null
[ $? -eq 0 ] && POINTS_B=$( calc $POINTS_B + 0.2 )

head -n 2 out1.txt 2>/dev/null | tail -n 1 >o 2>/dev/null
diff -bBw $ans1_2 o >/dev/null 2>/dev/null
[ $? -eq 0 ] && POINTS_B=$( calc $POINTS_B + 0.4 )

head -n 3 out1.txt 2>/dev/null | tail -n 1 >o 2>/dev/null
diff -bBw $ans1_3 o >/dev/null 2>/dev/null
[ $? -eq 0 ] && POINTS_B=$( calc $POINTS_B + 1 )

timeout 20 ./downloadContents.sh $test2 $d2 >out1.txt 2>/dev/null

rm "$d2/${test2}index.php" 2>/dev/null # Did this because there was a view count on this page

diff -bBw -r ../truth/B/dir2/ dir2/ >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_B++
[ -s urlReport.json ]
[ $? -eq 0 ] && POINTS_B=$( calc $POINTS_B + 0.4 )

head -n 1 out1.txt >o 2>/dev/null
diff -bBw $ans2_1 o >/dev/null 2>/dev/null
[ $? -eq 0 ] && POINTS_B=$( calc $POINTS_B + 0.2 )

head -n 2 out1.txt 2>/dev/null | tail -n 1 >o 2>/dev/null
diff -bBw $ans2_2 o >/dev/null 2>/dev/null
[ $? -eq 0 ] && POINTS_B=$( calc $POINTS_B + 0.4 )

head -n 3 out1.txt 2>/dev/null | tail -n 1 >o 2>/dev/null
diff -bBw $ans2_3 o >/dev/null 2>/dev/null
[ $? -eq 0 ] && POINTS_B=$( calc $POINTS_B + 1 )

calc 2.5*$POINTS_B

rm -f downloadContents.sh pdfWordCounter. >/dev/null 2>/dev/null
cd ..
rm -rf outdir/ 2>/dev/null
rm -rf Task4/ 2>/dev/null
