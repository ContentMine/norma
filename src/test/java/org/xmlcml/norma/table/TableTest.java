package org.xmlcml.norma.table;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.xml.XMLUtil;

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
		// proposed new table structure /ctree/tables/table%d/table.svg
		String cmd = "--project "+targetDir+" --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg.*$ --outputDir target/pdftable01/ --transform svgtable2html";
		new Norma().run(cmd);
		
	}
	
	@Test
	/** iterate over whole CProject
	 * 
	 */
	@Ignore // production
	public void testCProject() {
		// these ones have single text characters
		File sourceDir = new File("../../cm-ucl/corpus-oa-pmr/");
		if (sourceDir.exists()) {
			File targetDir = new File("../../cm-ucl/corpus-oa-pmr-v01/");
//			File targetDir = new File("target/corpus-oa-pmr-v01/");
			CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
			// note historical regex /ctree/table%d.svg
			String cmd = "--project "+targetDir+" --fileFilter ^.*/table\\d+(cont)?\\.svg.*$ --transform svgtable2html";
			LOG.debug("running norma");
			new Norma().run(cmd);
		} else {
			LOG.debug("no data, skipped");
		}
	}
	
	/** align rows and columns
	 * 
	 * @param inputFile
	 * @return
	 * @throws IOException 
	 */
	@Test
	public void testRowAndColumns() throws IOException {
		File inputFile = new File(NormaFixtures.TEST_TABLE_DIR, "svg/10.1007_s00213-015-4198-1.svg");
		SVGTable2HTMLConverter converter = new SVGTable2HTMLConverter();
		converter.readInput(inputFile);
		HtmlElement htmlElement = converter.convert();
		File file = new File(NormaFixtures.TARGET_DIR, "table/svg/10.1007_s00213-015-4198-1.svg.html");
		XMLUtil.debug(htmlElement, file, 1);
	}
	
	@Test
	/** aggregate into HTML display
	 * (a) creates table.svg.html from table.svg
	 * (b) iterates over all (table.svg and table.svg.html) pairs to create a combined table
	 *   with both
	 */
	public void testHtmlDisplay() {
		boolean clean = false;
		File sourceDir = NormaFixtures.TEST_PDFTABLE_DIR;
		File targetDir = new File("target/pdftable1/");
		LOG.debug("Target: "+targetDir);
		if (clean) {
			CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
			new Norma().run("--project "+targetDir+" --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg.*$"
				+ " --outputDir "+"target/pdftable01/"
				+ " --transform svgtable2html");
		}
		String cmd = "--project "+targetDir+
				" --fileFilter ^.*tables/table\\d+$"
				+ " --output  ./tableRow.html"
				+ " --htmlDisplay"
				+ " ^.*/table.png"
//				+ " ^.*tables/table\\d+/table.annot.svg"
//				+ " ^.*tables/table\\d+/table.svg"
				+ " ^.*/table.svg.html";
		new Norma().run(cmd);
		
	}
	
	@Test
	/** aggregate into TabbedButton display
	 * 
	 */
	public void testHtmlAggregate() {
		boolean clean = false;
		File sourceDir = NormaFixtures.TEST_PDFTABLE_DIR;
		File targetDir = new File("target/pdftable1/");
		if (clean) {
			CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
			/** make the *.svg.html as we have cleaned the directory */
			new Norma().run("--project "+targetDir+" --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg.*$"
					+ " --outputDir "+"target/pdftable01/"
					+ " --transform svgtable2html");
		}
		String cmd = "--project "+targetDir+
				" --fileFilter ^.*tables/table\\d+$"
				+ " --output  ./tableRow.html"
				+ " --htmlDisplay"
				+ " ^.*/table.png"
//				+ " ^.*tables/table\\d+/table.annot.svg"
//				+ " ^.*tables/table\\d+/table.svg"
				+ " ^.*/table.svg.html";
		
		new Norma().run(cmd);
		cmd = "--project "+targetDir
				+ " --output  tables/tableView.html"
				+ " --htmlAggregate ^.*tables/table\\d+/tableRow.html";
		new Norma().run(cmd);
	}
	
	

	

	
}
