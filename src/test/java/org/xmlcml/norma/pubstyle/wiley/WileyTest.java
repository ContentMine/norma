package org.xmlcml.norma.pubstyle.wiley;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;

public class WileyTest {
	
	private static final Logger LOG = Logger.getLogger(WileyTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	static String PUB0 = "wiley";
	static String PUB = "wiley";
	static File TARGET = new File(NormaFixtures.TARGET_PUBSTYLE_DIR, PUB);
	static File TEST = new File(NormaFixtures.TEST_PUBSTYLE_DIR, PUB);
	static File WIL_TEST = new File(TEST, "test");

	@Test
	public void testHtml2Scholarly() {
		NormaFixtures.htmlTidy(WIL_TEST, TARGET); 
	}

	@Test
	public void testHtml2Scholarly2StepConversion() {
		NormaFixtures.tidyTransform(WIL_TEST, TARGET, PUB0);
	}
	
}
