package org.xmlcml.norma.pubstyle.springer;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;

public class SpringerTest {
	
	private static final Logger LOG = Logger.getLogger(SpringerTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	static String PUB0 = "sp";
	static String PUB = "springer";
	static String PUB1 = PUB+"/clean";
	static File TARGET = new File(NormaFixtures.TARGET_PUBSTYLE_DIR, PUB);
	static File TARGET1 = new File(NormaFixtures.TARGET_PUBSTYLE_DIR, PUB1);
	static File TEST = new File(NormaFixtures.TEST_PUBSTYLE_DIR, PUB);
	static File TEST1 = new File(TEST, "ccby");

	@Test
	public void testHtml2Scholarly() {
		NormaFixtures.copyToTargetRunHtmlTidy(TEST1, TARGET); 
	}

	@Test
	public void testHtml2Scholarly2StepConversion() {
		NormaFixtures.copyToTargetRunTidyTransformWithStylesheetSymbolRoot(TEST1, TARGET, PUB0);
	}
	
	@Test
	// FIXME
	@Ignore // junit.org.AssertionFailedError: failed convert using: springer2html


	public void testHtml2Scholarly2StepConversionClean() throws IOException {
		NormaFixtures.tidyTransformAndClean(TEST1, TARGET1, PUB);
	}
	

}
