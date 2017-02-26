package org.xmlcml.norma.table;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
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
	/** just to recap how iteration works
	 * 
	 */
	@Ignore
	public void testPDFTable0() throws IOException {
		File sourceDir = NormaFixtures.TEST_PDFTABLE0_DIR;
		File targetDir = new File("target/pdftable0/");
		File oldSVG = new File("target/svg/"); // FIX this
		FileUtils.deleteDirectory(oldSVG);
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		// runs pdf2svg and svg2xml
		String cmd = "--project "+targetDir+" -i fulltext.pdf -o zzzz.html --transform pdf2html";
		new Norma().run(cmd);
		
	}
	
	@Test
	/** file filter to iterate over all files of a type
	 * 
	 */
	public void testFileFilter() {
		File sourceDir = NormaFixtures.TEST_PDFTABLE_DIR;
		File targetDir = new File("target/pdftable1/");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		String cmd = "--project "+targetDir+" --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg.*$ --outputDir target/pdftable01/ --transform svgtable2html";
		new Norma().run(cmd);
		
	}
	
}
