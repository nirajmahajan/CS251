#! /bin/bash

cp Task1/isPrimeYear.sh . >/dev/null 2>/dev/null
cp Task1/numberOfPrimeYears.txt . >/dev/null 2>/dev/null

chmod 777 isPrimeYear.sh >/dev/null 2>/dev/null
timeout 1 bash isPrimeYear.sh 79 2>/dev/null > myout1.txt
timeout 3 bash isPrimeYear.sh 691 2>/dev/null > myout2.txt
timeout 3 bash isPrimeYear.sh 937 2>/dev/null > myout3.txt
timeout 5 bash isPrimeYear.sh 8677 2>/dev/null > myout4.txt
timeout 7 bash isPrimeYear.sh 44537 2>/dev/null > myout5.txt
timeout 1 bash isPrimeYear.sh 1 2>/dev/null > myout6.txt
timeout 2 bash isPrimeYear.sh 57 2>/dev/null > myout7.txt
timeout 3 bash isPrimeYear.sh 667 2>/dev/null > myout8.txt
timeout 5 bash isPrimeYear.sh 2047 2>/dev/null > myout9.txt
timeout 5 bash isPrimeYear.sh 3507 2>/dev/null > myout10.txt
timeout 1 bash isPrimeYear.sh Taylor_Swift!! 2>/dev/null > myout11.txt
timeout 1 bash isPrimeYear.sh A!girl#has@no_Name,007 2>/dev/null > myout12.txt

POINTS_A=0
diff -bBw myout1.txt truth/truth_1_5.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout2.txt truth/truth_1_5.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout3.txt truth/truth_1_5.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout4.txt truth/truth_1_5.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout5.txt truth/truth_1_5.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout6.txt truth/truth_6_10.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout7.txt truth/truth_6_10.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout8.txt truth/truth_6_10.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout9.txt truth/truth_6_10.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout10.txt truth/truth_6_10.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout11.txt truth/truth_11_12.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++
diff -bBw myout12.txt truth/truth_11_12.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_A++

POINTS_B=0
diff -bBw numberOfPrimeYears.txt truth/primeCountAnswer.txt >/dev/null 2>/dev/null
[ $? -eq 0 ] && let POINTS_B+=3

printf "$POINTS_A\t$POINTS_B\t\n"

rm -f isPrimeYear.sh
rm -f numberOfPrimeYears.txt
rm -f myout*.txt
rm -f -r Task1/
