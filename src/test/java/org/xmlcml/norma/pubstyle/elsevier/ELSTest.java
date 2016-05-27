package org.xmlcml.norma.pubstyle.elsevier;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;

public class ELSTest {
	
	private static final Logger LOG = Logger.getLogger(ELSTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	static String PUB0 = "els";
	static String PUB = "elsevier";
	static File TARGET = new File(NormaFixtures.TARGET_PUBSTYLE_DIR, PUB);
	static File TEST = new File(NormaFixtures.TEST_PUBSTYLE_DIR, PUB);
	static File ELS_TEST = new File(TEST, "test");

	@Test
	public void testHtml2Scholarly() {
		NormaFixtures.htmlTidy(ELS_TEST, TARGET); 
	}

	@Test
	public void testHtml2Scholarly2StepConversion() {
		NormaFixtures.tidyTransform(ELS_TEST, TARGET, PUB0);
	}
	
}
