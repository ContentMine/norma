package org.xmlcml.norma.pubstyle.nature;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;

@Ignore // closed access
public class NatureTest {
	
	private static final Logger LOG = Logger.getLogger(NatureTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	static String PUB0 = "nature";
	static String PUB = "nature";
	static String PUB1 = PUB+"/clean";
	static File TARGET = new File(NormaFixtures.TARGET_PUBSTYLE_DIR, PUB);
	static File TARGET1 = new File(NormaFixtures.TARGET_PUBSTYLE_DIR, PUB1);
	static File TEST = new File(NormaFixtures.TEST_PUBSTYLE_DIR, PUB);
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

}
