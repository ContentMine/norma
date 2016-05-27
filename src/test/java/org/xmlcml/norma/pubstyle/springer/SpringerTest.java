package org.xmlcml.norma.pubstyle.springer;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;

public class SpringerTest {
	
	private static final Logger LOG = Logger.getLogger(SpringerTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	static String PUB0 = "sp";
	static String PUB = "springer";
	static File TARGET = new File(NormaFixtures.TARGET_PUBSTYLE_DIR, PUB);
	static File TEST = new File(NormaFixtures.TEST_PUBSTYLE_DIR, PUB);
	static File SPR_TEST = new File(TEST, "test");

	@Test
	public void testHtml2Scholarly() {
		NormaFixtures.htmlTidy(SPR_TEST, TARGET); 
	}

	@Test
	public void testHtml2Scholarly2StepConversion() {
		NormaFixtures.tidyTransform(SPR_TEST, TARGET, PUB0);
	}
	
}
