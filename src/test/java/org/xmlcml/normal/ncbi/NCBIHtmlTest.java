package org.xmlcml.normal.ncbi;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.norma.NormaArgProcessor;

public class NCBIHtmlTest {

	private static final Logger LOG = Logger.getLogger(NCBIHtmlTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testNCBIProject() throws IOException {
		File ursus = new File(Fixtures.TEST_NORMA_DIR, "shtml/ursus");
		File shtml = new File("target/ursus1/");
		FileUtils.copyDirectory(ursus, shtml);
		NormaArgProcessor normaArgProcessor = new NormaArgProcessor();
//		String args = "--project "+shtml.toString()+ " -i fulltext.xml  --transform ncbi-jats2html -o scholarly.html";
		String args = "--project "+shtml.toString()+ " -i fulltext.xml  --transform jats2shtml -o scholarly.html";
		LOG.debug(args);
		normaArgProcessor.parseArgs(args);
		normaArgProcessor.runAndOutput();
	}
	
}
