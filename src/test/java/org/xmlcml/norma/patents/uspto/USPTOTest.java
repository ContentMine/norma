package org.xmlcml.norma.patents.uspto;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.norma.NormaArgProcessor;

public class USPTOTest {
	private static final Logger LOG = Logger.getLogger(USPTOTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	

	@Test
	public void testReadCTree() throws IOException {
		File target = new File("target/us08978/US08978162-20150317");
		FileUtils.copyDirectory(new File(Fixtures.TEST_USPTO08978_DIR, "US08978162-20150317/"), target);
		String args = "-i fulltext.xml --transform uspto2html -o scholarly.html --ctree "+target; 
		NormaArgProcessor norma = new NormaArgProcessor(args);
		norma.runAndOutput();
	}

	@Test
	public void testReadCProject() throws IOException {
		File target = new File("target/us08978/");
		FileUtils.copyDirectory(Fixtures.TEST_USPTO08978_DIR, target);
		String args = "-i fulltext.xml --transform uspto2html -o scholarly.html --project "+target; 
		NormaArgProcessor norma = new NormaArgProcessor(args);
		norma.runAndOutput();
	}
}
