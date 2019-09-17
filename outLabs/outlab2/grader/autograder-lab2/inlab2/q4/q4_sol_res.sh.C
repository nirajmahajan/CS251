tempD1="MYFILE.D1"
tempD2="MYFILE.D2"
if [ -s $1 ];
then

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

}' $1 >$tempD1

awk '{print|"sort -u"}' $tempD1 > $tempD2


cat $tempD2


rm $tempA $tempB $tempC $tempD1 $tempD2

else
echo ""
fi