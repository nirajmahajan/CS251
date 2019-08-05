make_n_with_zero_begin(){
	local n=$1
	local ans
	local rand=$( od -vAn -N2 -tu2 < /dev/urandom)
	if [ n == 1 ]
	then
		ans=$(($rand%10))
	else
		n=$((n-1))
		ans=$( make_n_with_zero_begin n )
		ans=$((10*ans))
		n=$(($rand%10))
		ans=$((ans+n))
	fi
	echo $ans
}
make_n_without_zero_begin(){
	local num=$1
	local ans
	rand=$( od -vAn -N2 -tu2 < /dev/urandom)
	if [ $num == 1 ]
	then
		ans=$(($rand%10))
		while [ $ans == 0 ]
		do 
			rand=$( od -vAn -N2 -tu2 < /dev/urandom)
			ans=$(($rand%10))
		done
	else
		num=$((num-1))
		ans=$( make_n_without_zero_begin $num )
		num=$(($rand%10))
		ans="$ans$num"
	fi
	echo $ans
}

echo $( make_n_without_zero_begin $1 )