package org.xmlcml.norma.pubstyle.generic;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.pubstyle.getpapers.GetPapers;

public class GetPapersTest {

	private static final Logger LOG = Logger.getLogger(GetPapersTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}


	@Test
	public void testGetPapers() throws IOException{
		// for info
		String query = "getpapers --api eupmc -q 'anopheles FIRST_PDATE:[2014-06-01 TO 2014-06-07]' -x --outdir anopheles";
		// results in src/test/resources/org/xmlcml/norma/pubstyle/
		File anophelesDir = new File(NormaFixtures.TEST_PUBSTYLE_DIR, "getpapers/anopheles");
		File resultsJsonFile = new File(anophelesDir, "eupmc_results.json");
		Assert.assertTrue(resultsJsonFile.exists());
		GetPapers getPapers = new GetPapers();
		getPapers.setJsonPath(GetPapers.PMCID);
	    getPapers.mapJsonArrayToFiles(anophelesDir, resultsJsonFile);

	}

}
