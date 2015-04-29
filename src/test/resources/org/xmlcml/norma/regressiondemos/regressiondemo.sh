#!/bin/sh

# archetypal demos/regression tests for ami-plugins

# clean old directories
rm -rf temp*

# create new CMDirectories

# single XML file
rm -rf cmdir_xml
../../../../../../../target/appassembler/bin/norma -i singleFiles/test_xml_1471-2148-14-70.xml -o cmdir_xml

rm -rf cmdirs_xml
../../../../../../../target/appassembler/bin/norma -i \
    singleFiles/test_xml_1471-2148-14-70.xml \
    singleFiles/plosone_0115884.xml \
	 -o cmdirs_xml

rm -rf cmdirs_all
../../../../../../../target/appassembler/bin/norma -i \
    	singleFiles/test_xml_1471-2148-14-70.xml \
	    singleFiles/test_pdf_1471-2148-14-70.pdf \
    	singleFiles/plosone_0115884.xml \
	 -o cmdirs_all

# convert PDF to TXT (
../../../../../../../target/appassembler/bin/norma \
    	-q cmdirs_all/test_pdf_1471-2148-14-70 \
		--xsl pdf2txt \
		-i fulltext.pdf \
		-o fulltext.pdf.txt
ls -lt cmdirs_all/test_pdf_1471-2148-14-70/fulltext.pdf.txt

# create scholarly.html for 9 publishers

echo norma acp
cp -R quickscrapeDirs/acp/acp-15-1013-2015 temp-acp
rm temp-acp/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-acp -i fulltext.xml -o scholarly.html --xsl nlm2html
ls -lt temp-acp/scholarly.html

echo norma bmc
cp -R quickscrapeDirs/bmc/1471-2148-14-70 temp-bmc
rm temp-bmc/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-bmc -i fulltext.xml -o scholarly.html --xsl bmc2html
ls -lt temp-bmc/scholarly.html

echo norma elife
cp -R quickscrapeDirs/elife/e04407 temp-elife
rm temp-elife/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-elife -i fulltext.xml -o scholarly.html --xsl nlm2html
ls -lt temp-elife/scholarly.html

echo norma f1000research
cp -R quickscrapeDirs/f1000research/3-190 temp-f1000research
rm temp-f1000research/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-f1000research -i fulltext.xml -o scholarly.html --xsl nlm2html
ls -lt temp-f1000research/scholarly.html

echo norma frontiers
cp -R quickscrapeDirs/frontiers/fpsyg-05-01582 temp-frontiers
rm temp-frontiers/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-frontiers -i fulltext.xml -o scholarly.html --xsl nlm2html
ls -lt temp-frontiers/scholarly.html

echo norma hindawi
cp -R quickscrapeDirs/hindawi/247835 temp-hindawi
rm temp-hindawi/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-hindawi -i fulltext.xml -o scholarly.html --xsl nlm2html
ls -lt temp-hindawi/scholarly.html

echo norma mdpi
cp -R quickscrapeDirs/mdpi/04-00932 temp-mdpi
rm temp-mdpi/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-mdpi -i fulltext.xml -o scholarly.html --xsl nlm2html
ls -lt temp-mdpi/scholarly.html

echo norma peerj
cp -R quickscrapeDirs/peerj/727 temp-peerj
rm temp-peerj/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-peerj -i fulltext.xml -o scholarly.html --xsl nlm2html
ls -lt temp-peerj/scholarly.html

echo norma pensoft
cp -R quickscrapeDirs/pensoft/4478 temp-pensoft
rm temp-pensoft/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-pensoft -i fulltext.xml -o scholarly.html --xsl nlm2html
ls -lt temp-pensoft/scholarly.html

echo norma plosone
cp -R quickscrapeDirs/plosone/journal.pone.0115884.norma temp-plosone
rm temp-plosone/scholarly.html
../../../../../../../target/appassembler/bin/norma -q temp-plosone -i fulltext.xml -o scholarly.html --xsl nlm2html
ls -lt temp-plosone/scholarly.html

