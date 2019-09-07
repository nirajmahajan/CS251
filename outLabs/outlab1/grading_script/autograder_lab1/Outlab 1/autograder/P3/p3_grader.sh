#! /bin/bash

cp Task3/helpGroupTheCsv.sh . >/dev/null 2>/dev/null
cp -f source/input* . >/dev/null 2>/dev/null
chmod 777 helpGroupTheCsv.sh >/dev/null 2>/dev/null

POINTS_A=0

for i in {1..4}
do
	timeout 2 ./helpGroupTheCsv.sh input"$i".csv myout"$i".csv >/dev/null 2>/dev/null
	diff -bBw myout"$i".csv truth/out"$i".csv >/dev/null 2>/dev/null
	[ $? -eq 0 ] && let POINTS_A+=5
	rm -f myout"$i".csv
	rm -f input"$i".csv
done

printf "$POINTS_A\t\n"

rm -f helpGroupTheCsv.sh
rm -f *.csv
rm -f *.txt
rm -f -r Task3/
