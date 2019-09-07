#! /bin/bash

POINTS_A=0
POINTS_B=0

test_words=(mkdir cat touch pwd chmod head tail more less cp mv grep find df sleep jobs fg bg ps rmdir uptime uname whereis which ping tee fgrep ifconfig wget vimtutor)

for i in ${!test_words[@]}
do
	count="$(cat *.script | grep -i -o ${test_words["$i"]} | wc -l)" 2>/dev/null
	[ $count -gt 0 ] && let POINTS_A+=3
	[ $count -eq 0 ] && printf "${test_words["$i"]} "
done

count="$(cat *.script | wc -l)" 2>/dev/null
[ $count -gt 30 ] && let POINTS_B+=10

printf "\t$POINTS_A\t$POINTS_B\t\n"
rm -f *.script
