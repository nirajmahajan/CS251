#!/bin/bash
read ans
for key in {A..Z}
do
echo "$key $ans"
ans=$(echo $ans | tr A-Z ZA-Y)
done
read key
read ans
chmod 755 *
echo $key | cat > decodedCipher.txt
echo $ans | cat >> decodedCipher.txt
sed -i '/^$/d' decodedCipher.txt