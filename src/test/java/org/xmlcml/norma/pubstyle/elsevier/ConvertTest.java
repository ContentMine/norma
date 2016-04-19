package org.xmlcml.norma.pubstyle.elsevier;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;

@Ignore // too long for tests
public class ConvertTest {

	private static final Logger LOG = Logger.getLogger(ConvertTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testConvert() throws IOException {
		File project = new File(NormaFixtures.TEST_PUBSTYLE_DIR, "elsevier/tgac0");
		File target = new File("target/elsevier/tgac0");
		FileUtils.copyDirectory(project, target);
		LOG.debug(target);
		String args = "--project "+target+" -i fulltext.pdf -o fulltext.pdf.html --transform pdf2html";
		Norma norma = new Norma();
		norma.run(args);
		
	}
	
}
