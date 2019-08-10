a=$1/*.txt
for var in $a
do
	out=$( awk 'BEGIN{FS=":"}NR==4{print $2}' $var | sed 's/ //g' )
	mv -t ./$out $var >& /dev/null
	if [ $? -eq 1 ]
	then
		mkdir -p ./$out
		mv $var ./$out
	fi
done