#! /bin/bash

cp Task5/recursiveSearch.sh . >/dev/null 2>/dev/null
rm -f -r Data/
cp -r source/Data/ . >/dev/null 2>/dev/null
chmod 777 recursiveSearch.sh >/dev/null 2>/dev/null

POINTS_A=0
./recursiveSearch.sh 2>/dev/null > myout0.txt
[ $? -eq 1 ] && let POINTS_A+=2
diff -bBw myout0.txt truth/out0.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A+=3


timeout 1 ./recursiveSearch.sh "a" "an" "the" 2>/dev/null | sort  2>/dev/null > myout1.txt
timeout 3 ./recursiveSearch.sh "laughed" "cried" "CHOKED" 2>/dev/null | sort  2>/dev/null > myout2.txt
timeout 2 ./recursiveSearch.sh "vel" "sem" 2>/dev/null | sort  2>/dev/null > myout3.txt
timeout 3 ./recursiveSearch.sh "funny" "Employed" "morNing" 2>/dev/null | sort  2>/dev/null > myout4.txt
timeout 5 ./recursiveSearch.sh "Godfrey" "Norton" "briony" "repeated" "fear" 2>/dev/null | sort  2>/dev/null > myout5.txt
timeout 5 ./recursiveSearch.sh "Holmes" "irene" "Love" "EMOTION" "perfect" "world" 2>/dev/null | sort  2>/dev/null > myout6.txt
rm -f recursiveSearch.sh

POINTS_B=0
for i in {1..4}
do
	diff -bBw myout"$i".txt truth/out"$i".txt >/dev/null 2>/dev/null
	[ $? -eq 0 ] && let POINTS_B+=3
	rm -f myout"$i".txt
done

for i in {5..6}
do
	diff -bBw myout"$i".txt truth/out"$i".txt >/dev/null 2>/dev/null
	[ $? -eq 0 ] && let POINTS_B+=4
	rm -f myout"$i".txt
done

printf "$POINTS_A\t$POINTS_B\t\n"

rm -f recursiveSearch.sh
rm -f myout*.txt
rm -f -r Task5/
rm -f -r Data/
