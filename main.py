# Extract data from pcap-file
import binascii
from scapy.all import *

pkts_list = rdpcap("mitschnitt_1.pcap")

''' Test-Code - Formatting 
print(len(pkts_list))
print(pkts_list[0]['Raw'])
print(binascii.hexlify(bytes(pkts_list[0]['Raw'].load)))
print(pkts_list[0]['Raw'].load)
'''

file = 'rawData_2.csv'

with open(file, 'w') as f:
    for row in pkts_list:
        if(row.dst == 'ff:ff:ff:ff:ff:ff'):
            line = binascii.hexlify(bytes(row['Raw'].load)).decode('utf-8') + '\n'
            f.write(line)
    f.close()

