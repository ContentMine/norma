package org.xmlcml.norma.cproject;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.xmlcml.cmine.files.CContainer;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.norma.InputTest;

public class ResultsJSONTest {

	private static final Logger LOG = Logger.getLogger(ResultsJSONTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testSplitAndNormalize() {
		CContainer cProject = new CProject(new File(Fixtures.TEST_MISC_DIR, "cproject"));
		File file = cProject.getAllowedChildFile(CProject.EUPMC_RESULTS_JSON);
		
	}
}
