package org.xmlcml.norma.pubstyle.rs;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;

import net.sf.saxon.TransformerFactoryImpl;

//@Ignore // no open -access papers
public class RSTest {
	private static final Logger LOG = Logger.getLogger(RSTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	static String PUB0 = "rs";
	static String PUB = "rs";
	static String PUB1 = PUB+"/clean";
	static File TARGET = new File(NormaFixtures.TARGET_PUBSTYLE_DIR, PUB);
	static File TARGET1 = new File(NormaFixtures.TARGET_PUBSTYLE_DIR, PUB1);
	// static File TEST = new File(NormaFixtures.TEST_PUBSTYLE_DIR, PUB); // change back to this when open
	static File TEST = new File(NormaFixtures.EXAMPLES_DIR, PUB); // change to 
	//static File TEST1 = new File(TEST, "ccby"); // change back to this when open
	static File TEST1 = new File(TEST, "closed");

	@Test
	public void testHtml2Scholarly() {
		NormaFixtures.htmlTidy(TEST1, TARGET); 
	}

	@Test
	public void testHtml2Scholarly2StepConversion() {
		NormaFixtures.tidyTransform(TEST1, TARGET, PUB0);
	}
	
	@Test
	public void testHtml2Scholarly2StepConversionClean() throws IOException {
		NormaFixtures.tidyTransformAndClean(TEST1, TARGET1, PUB);
	}
	
	
	@Test
	@Ignore
	public void testBadStylesheet() throws Exception {
		File CTREE_DIR = new File("target/pubstyle/rs/277_1686_1309");
		File infile = new File(CTREE_DIR, "fulltext.xhtml");
		Source source = new StreamSource(infile);
		File outfile = new File(CTREE_DIR, "schol1.html");
		Result result = new StreamResult(outfile);
		Transformer transformer = new TransformerFactoryImpl().newTransformer(
				new StreamSource("src/main/resources/org/xmlcml/norma/pubstyle/rs/toHtml.xsl"));
		transformer.transform(source, result);
	}
	
}
