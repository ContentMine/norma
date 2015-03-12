#!bin/sh

# prints help
target/appassembler/bin/norma

# reads a file and creates a QSN directory
rm -rf target/bmctest  # clean target directory
target/appassembler/bin/norma -i examples/trialsjournal_15_1_511.pdf -o target/bmctest/
echo "we have created fulltext.pdf in new directory"
ls -lt target/bmctest/trialsjournal_15_1_511/ 

echo
echo "=== transform an existing QSN directory ==="
echo
cd examples/http_www.trialsjournal.com_content_16_1_1
pwd
ls -lt
cd ../..

echo
echo "copy to temporary location"
echo
rm -rf target/bmctest # clean old target
cp -R examples/http_www.trialsjournal.com_content_16_1_1 target/bmctest/
cd target/bmctest
echo
pwd
ls -lt
cd ../../
echo
echo " now transform the fulltext.xml into scholarly.html"
echo
target/appassembler/bin/norma -q target/bmctest/ -i fulltext.xml -o scholarly.html --xsl bmc2html

echo
cd target/bmctest/
pwd
echo
ls -lt

cd ../..

