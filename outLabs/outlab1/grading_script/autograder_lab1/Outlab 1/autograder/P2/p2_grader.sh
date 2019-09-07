#! /bin/bash

POINTS_A=0
POINTS_B=0
POINTS_C=0
cp truth/passed.txt Task2/A/ 2>/dev/null
cp truth/passed.txt Task2/B/ 2>/dev/null
cp truth/ans.txt Task2/B/ 2>/dev/null
cd Task2/A/ >/dev/null 2>/dev/null
chmod 777 randomNumber.sh >/dev/null 2>/dev/null

timeout 2 ./randomNumber.sh 5 >out1.txt 2>/dev/null
sed -i 's/^[1-9]\([0-9]\)\{4\}/PASSED/g' out1.txt

timeout 4 ./randomNumber.sh 40 >out2.txt 2>/dev/null
sed -i 's/^[1-9]\([0-9]\)\{39\}/PASSED/g' out2.txt 2>/dev/null

timeout 7 ./randomNumber.sh 100 >out3.txt 2>/dev/null
sed -i 's/^[1-9]\([0-9]\)\{99\}/PASSED/g' out3.txt 2>/dev/null

timeout 1 ./randomNumber.sh 1 >out4.txt 2>/dev/null
sed -i 's/^[0-9]/PASSED/g' out4.txt 2>/dev/null

timeout 10 ./randomNumber.sh 2000 >out5.txt 2>/dev/null
sed -i 's/^[1-9]\([0-9]\)\{1999\}/PASSED/g' out5.txt 2>/dev/null

timeout 10 ./randomNumber.sh 20000 >out6.txt 2>/dev/null
sed -i 's/^[1-9]\([0-9]\)\{19999\}/PASSED/g' out6.txt 2>/dev/null


diff -bBw out1.txt passed.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++

diff -bBw out2.txt passed.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++

diff -bBw out3.txt passed.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++

diff -bBw out4.txt passed.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++

diff -bBw out5.txt passed.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++

diff -bBw out6.txt passed.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++

rm out*.txt 2>/dev/null

cd ../B/ 2>/dev/null
chmod 777 findTheAnswer.sh >/dev/null 2>/dev/null

# 5 trials will be made and one must consistently give right output to pass, with more timeout each time
timeout 5 ./findTheAnswer.sh >/dev/null 2>/dev/null

tail -n 1 howFarFromTruth.txt >outans.txt 2>/dev/null
sed -i '/[1-9]\([0-9]\)\{1\}/d' howFarFromTruth.txt 2>/dev/null
echo "PASSED" >>howFarFromTruth.txt

diff -bBw howFarFromTruth.txt passed.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_B+=2

diff -bBw outans.txt ans.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_B+=2


rm -f out*.txt 2>/dev/null
rm -f howFarFromTruth.txt 2>/dev/null

cd ../C/ 2>/dev/null

chmod 777 expectedDistance.sh >/dev/null 2>/dev/null

avg=90

timeout 50 ./expectedDistance.sh 50 1>expectedDistance.txt 2>/dev/null

x=$( cat expectedDistance.txt 2>/dev/null)
x=$( echo $x | sed 's/\..*//g' )
abs=$(echo $x-$avg 2>/dev/null | bc 2>/dev/null | tr -d - 2>/dev/null)

if [[ abs -lt 50 ]]; then
	let POINTS_C+=2
fi
if [[ abs -lt 20 ]]; then
	let POINTS_C+=2
fi

if [[ abs -lt 10 ]]; then
	let POINTS_C+=1
fi

printf "$POINTS_A\t$POINTS_B\t$POINTS_C\t\n"

rm -f expectedDistance.txt 2>/dev/null
cd ../..	
rm -f -r Task2/ 2>/dev/null
