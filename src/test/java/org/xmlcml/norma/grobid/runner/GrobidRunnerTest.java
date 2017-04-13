package org.xmlcml.norma.grobid.runner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.grobid.core.main.batch.GrobidMain;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.files.CProject;
import org.xmlcml.cproject.files.RegexPathFilter;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.norma.NormaFixtures;

import junit.framework.Assert;

public class GrobidRunnerTest {

	private static final Logger LOG = Logger.getLogger(GrobidRunnerTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	@Ignore // GROBID problems
	public void testSinglePDF() throws Exception {
		File pdfDirectory = new File("src/test/resources/org/xmlcml/norma/grobid/sample/");
		File targetDir = new File(NormaFixtures.TARGET_DIR, "grobid/sample");
		CMineTestFixtures.cleanAndCopyDir(pdfDirectory, targetDir);	
		runGrobid(GrobidRunner.GROBID_HOME, targetDir);
	}

	private void runGrobid(File grobidHome, File pdfDirectory) throws IOException, Exception {
		String cmd = ""
				+ " -exe processFullText"
				+ " -gH " + grobidHome
				+ " -dIn " + pdfDirectory
				+ " -dOut " + pdfDirectory
				+ "";
		String[] args = cmd.split("\\s+");
		GrobidMain.main(args);
	}
	
	
}
