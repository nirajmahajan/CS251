tempD2="MYFILE.D2"

if [ -s $1 ];
then
    awk '{print|"sort -u"}' $1 > $tempD2


    cat $tempD2


    rm $tempD2

else
echo ""
fi
