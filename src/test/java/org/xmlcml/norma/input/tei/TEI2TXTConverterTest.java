package org.xmlcml.norma.input.tei;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.files.CTreeList;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by tom on 18/12/17.
 */
public class TEI2TXTConverterTest {
    private static String newline = System.getProperty("line.separator");
    	private static final Logger LOG = Logger.getLogger(TEI2TXTConverterTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

    @Test
    public void convertTEI2TXT() throws Exception {
        InputStream inputStream = new FileInputStream(new File("src/test/resources/org/xmlcml/norma/input/tei/minimal.xml"));
        String inputString = new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
        // System.out.print(inputString);
        TEI2TXTConverter converter = new TEI2TXTConverter();
        String outputString = converter.convertTEI2TXT(inputString);
        Assert.assertEquals("Test Test"+newline, outputString);
        System.out.print(outputString);
    }

    @Test
    public void convertTEI2TXTEnd2End() throws Exception {
        File testCTree = new File("target/teitest/CTree/");
		if (testCTree.exists()) FileUtils.forceDelete(testCTree);
		FileUtils.copyDirectory(new File(NormaFixtures.TEST_NORMA_DIR, "/input/tei/TestCTree"), testCTree);
		String args = "-q "+testCTree.toString()+
				" --transform tei2txt --input fulltext.tei.xml --output fulltext.pdf.tei.txt --standalone true";
		Norma norma = new Norma();
		norma.run(args);
		CTreeList cTreeList = norma.getArgProcessor().getCTreeList();
		Assert.assertNotNull(cTreeList);
		Assert.assertEquals("CTree/s",  1,  cTreeList.size());
		CTree cTree = cTreeList.get(0);
		List<File> files = cTree.listFiles(true);
		LOG.trace(cTree+"; "+files);
		File txtFile = new File(testCTree, "fulltext.pdf.tei.txt");
 		Assert.assertTrue(""+txtFile+" should exist", txtFile.exists());
    }
}