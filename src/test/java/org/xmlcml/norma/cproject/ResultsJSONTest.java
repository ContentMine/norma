package org.xmlcml.norma.cproject;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.cproject.files.CContainer;
import org.xmlcml.cproject.files.CProject;
import org.xmlcml.cproject.metadata.AbstractMetadata;
import org.xmlcml.norma.NormaFixtures;

public class ResultsJSONTest {

	private static final Logger LOG = Logger.getLogger(ResultsJSONTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testSplitAndNormalize() {
		CContainer cProject = new CProject(new File(NormaFixtures.TEST_MISC_DIR, "cproject"));
		File file = cProject.getAllowedChildFile(AbstractMetadata.Type.EPMC.getCProjectMDFilename());
	}
}
