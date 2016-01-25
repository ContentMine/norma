package org.xmlcml.norma.pubstyle.elife;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.pdfbox.util.XMLUtil;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;

public class ELifeTest {
	private static final Logger LOG = Logger.getLogger(ELifeTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testParsingError() throws FileNotFoundException, IOException {
		File elifeXML = new File(NormaFixtures.TEST_ELIFE_DIR, "e04407/fulltext.xml");
		XMLUtil.parse(new FileInputStream(elifeXML));
		// not valid XML
//		File elifeHtml = new File(NormaFixtures.TEST_ELIFE_DIR, "e04407/fulltext.html");
//		XMLUtil.parse(new FileInputStream(elifeHtml));
	}

}
