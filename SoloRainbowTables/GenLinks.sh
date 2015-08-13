for x in {0..255}
do 
	for y in {0..255}
	do 
		printf "SoloLink_33%02X%02X\n" $(($x ^ 0xf00)) $y $z
        done
done

