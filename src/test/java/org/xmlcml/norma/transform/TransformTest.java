package org.xmlcml.norma.transform;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;

public class TransformTest {

	

	private static final Logger LOG = Logger.getLogger(TransformTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	
	@Test
	public void testMissingFiles() throws Exception {
		
		File sourceDir = new File(NormaFixtures.TEST_NORMA_DIR, "transform/biserrula");
		File targetDir = new File("target/transform/biserrula");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		String cmd = "--project "+targetDir+" -i fulltext.xml  -o scholarly.html --transform nlm2html";
		new Norma().run(cmd);
		Assert.assertNull("XML should not exist", new CTree(new File(targetDir, "PMC3907879")).getExistingFulltextXML());
		Assert.assertNull("SHTML should not exist", new CTree(new File(targetDir, "PMC3907879")).getExistingScholarlyHTML());
		Assert.assertNotNull("XML should exist", new CTree(new File(targetDir, "PMC4062634")).getExistingFulltextXML());
		Assert.assertNotNull("SHTML should exist", new CTree(new File(targetDir, "PMC4062634")).getExistingScholarlyHTML());
		// this is the problem - created when no fulltext.xml
		Assert.assertNull("XML should not exist", new CTree(new File(targetDir, "PMC4062642")).getExistingFulltextXML());
		Assert.assertNull("SHTML should not exist",new CTree(new File(targetDir, "PMC4062642")).getExistingScholarlyHTML());
		
		Assert.assertNotNull("XML should exist", new CTree(new File(targetDir, "PMC4901225")).getExistingFulltextXML());
		Assert.assertNotNull("SHTML should exist", new CTree(new File(targetDir, "PMC4901225")).getExistingScholarlyHTML());

	}
	
	
}
