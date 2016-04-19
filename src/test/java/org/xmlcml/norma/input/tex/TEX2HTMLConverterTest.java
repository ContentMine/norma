package org.xmlcml.norma.input.tex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlcml.cmine.misc.CMineUtil;
import org.xmlcml.norma.NormaFixtures;

public class TEX2HTMLConverterTest extends XMLTestCase {
	
	
	private static final Logger LOG = Logger
			.getLogger(TEX2HTMLConverterTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
    @Test
//    @Ignore // till we install LatexML
    public void testConvertTex() throws InterruptedException, IOException, SAXException {
        // LaTeXML includes comments about the generator which vary on each run.
        // Ignore these.
        XMLUnit.setIgnoreComments(true);

        TEX2HTMLConverter converter = new TEX2HTMLConverter();
        File texFile = new File(NormaFixtures.TEST_NORMA_DIR + "/tex/sample.tex");
        File expectedXMLFile = new File(NormaFixtures.TEST_NORMA_DIR + "/tex/sample.tex.xhtml");
        String actualXML = null;
        Assert.assertTrue("text exists", texFile.exists());
    	actualXML = converter.convertTeXToHTML(texFile);
        if (actualXML != null) {
	        String expectedXML = new String(IOUtils.toByteArray(new FileInputStream(expectedXMLFile)));
	        Diff diff = new Diff(expectedXML, actualXML);
	
	        // LaTeXML output includes certain attributes/values which differ on each run.
	        // This current test only verifies the structure of the markup
	        diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());
	
	        assertXMLEqual(diff, true);
        }
    }

}
