package org.xmlcml.norma.input.tex;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlcml.norma.Fixtures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TEX2HTMLConverterTest extends XMLTestCase {
    @Test
    public void testConvertTex() throws InterruptedException, IOException, SAXException {
        // LaTeXML includes comments about the generator which vary on each run.
        // Ignore these.
        XMLUnit.setIgnoreComments(true);

        TEX2HTMLConverter converter = new TEX2HTMLConverter();
        File texFile = new File(Fixtures.TEST_NORMA_DIR + "/tex/sample.tex");
        File expectedXMLFile = new File(Fixtures.TEST_NORMA_DIR + "/tex/sample.tex.xhtml");
        String actualXML = converter.convertTeXToHTML(texFile);
        String expectedXML = new String(IOUtils.toByteArray(new FileInputStream(expectedXMLFile)));
        Diff diff = new Diff(expectedXML, actualXML);

        // LaTeXML output includes certain attributes/values which differ on each run.
        // This current test only verifies the structure of the markup
        diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());

        assertXMLEqual(diff, true);
    }
}
