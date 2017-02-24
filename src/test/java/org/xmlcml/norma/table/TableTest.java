package org.xmlcml.norma.table;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;

public class TableTest {
	private static final Logger LOG = Logger.getLogger(TableTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	@Ignore
	public void testPDFTables() {
		String root = "10.1016_j.pain.2014.08.023";
		File sourceDir = new File(NormaFixtures.TEST_PDFTABLE_DIR, root);
		File targetDir = new File("target/pdftable/"+root+"/");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		String cmd = "--project "+targetDir+" -i table%d.svg  -o table%d.html --transform pdfsvg2table";
		new Norma().run(cmd);
		
	}
}
