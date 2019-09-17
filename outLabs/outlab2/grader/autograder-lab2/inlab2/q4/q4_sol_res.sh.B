tempC="MYFILE.C"
tempD1="MYFILE.D1"
tempD2="MYFILE.D2"
if [ -s $1 ];
then

# sed -e "s/[[:punct:]]*\b[[:punct:]]*\([a-zA-Z[:punct:]]*\)/\1/g" $tempB >$tempC  #delete punctuations at start
# sed -i "s/\([a-zA-Z[:punct:]]*\)[[:punct:]]*\b[[:punct:]]*/\1/g" $tempC #delete trailing punctuations
# sed -i "s/^[[:punct:]][[:punct:]]*$//g" $tempC # remove lines with only punctuations
sed -e "s/^[[:punct:]]\+//g" $1 >$tempC  #delete punctuations at start
sed -i "s/[[:punct:]]\+$//g" $tempC #delete trailing punctuations
sed -i "/^[[:punct:]]*$/d" $tempC # remove lines with only punctuations

# sed -i "/^\s*$/d" $tempC # remove empty lines

awk  'BEGIN{

}
{
    if (!seen[$0])
        a[$0]=$0;
    seen[$0]++;

}
END{
    n=asorti(a, sorted)
    for(i in sorted)
        print sorted[i]

}' $tempC >$tempD1

awk '{print|"sort -u"}' $tempD1 > $tempD2


cat $tempD2


rm $tempA $tempB $tempC $tempD1 $tempD2
else
echo ""
fi