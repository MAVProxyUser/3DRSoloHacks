pyrit eval
pyrit -i dict.txt import_passwords
for each in `cat SSIDs.txt` ; do  pyrit -e $each create_essid; done
pyrit eval
pyrit batch
pyrit verify
pyrit -o SoloRainBowTable_Pyrit export_hashdb
