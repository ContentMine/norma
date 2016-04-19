package org.xmlcml.norma.shtml;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.NormaArgProcessor;

public class ScholarlyHtmlTest {

	private static final Logger LOG = Logger.getLogger(ScholarlyHtmlTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testScholarlyProject() throws IOException {
		File ursus = new File(NormaFixtures.TEST_NORMA_DIR, "shtml/ursus");
		File shtml = new File("target/shtml/");
		FileUtils.copyDirectory(ursus, shtml);
		NormaArgProcessor normaArgProcessor = new NormaArgProcessor();
		String args = "--project "+shtml.toString()+ " -i fulltext.xml  --transform nlm2html -o scholarly.html";
		LOG.trace(args);
		normaArgProcessor.parseArgs(args);
		normaArgProcessor.runAndOutput();
	}
	
}
