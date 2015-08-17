#Known Good SoloLink SSID's
#
# SoloLink_33F7EF
# SoloLink_33F8D8
# SoloLink_33F891
# SoloLink_33F948

for x in {0..15} # 0xf = 15
do 
	for y in {0..255}
	do 

		printf -v GeneratedSoloMAC "SoloLink_33F%01X%02X" $x $y 
		printf "$GeneratedSoloMAC\n"

        done
done

