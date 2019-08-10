#!/bin/bash
wget -O "summaryData.pdf" -q "$1"
chmod 755 *
pdftotext "summaryData.pdf"
sed -i 's/\f//g' summaryData.txt
rm "summaryData.pdf"
