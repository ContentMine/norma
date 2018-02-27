package org.xmlcml.norma.table;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.files.CProject;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.graphics.html.HtmlElement;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.svg2xml.table.TableContentCreator;
import org.xmlcml.xml.XMLUtil;


public class LargeTableTest {
	private static final Logger LOG = Logger.getLogger(LargeTableTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	/** just to recap how iteration works
	 * 
	 */
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
	public void testCProject() {
		// these ones have single text characters
		File sourceDir = new File("../../cm-ucl/corpus-oa-pmr/");
		/** only works for PMR */
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
	public void testCreateSvgHtml() {
		boolean clean = false;
		File sourceDir = NormaFixtures.TEST_PDFTABLE00_DIR;
		File targetDir = new File("target/pdftable00/");
		LOG.debug("Target: "+targetDir);
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		new Norma().run("--project "+targetDir+" --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg.*$"
			+ " --outputDir "+"target/pdftable00/"
			+ " --transform svgtable2html");
		
	}
	
	@Test
	/** aggregate into HTML display
	 * (a) creates table.svg.html from table.svg
	 * (b) iterates over all (table.svg and table.svg.html) pairs to create a combined table
	 *   with both
	 */
	public void testHtmlDisplay() {
		boolean clean = true;
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
		boolean clean = true;
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
				+ " --output tables/tableView.html"
				+ " --htmlAggregate ^.*tables/table\\d+/tableRow.html";
		new Norma().run(cmd);
	}
        	
	@Test
	public void testMenu() {
		File targetDir = new File("target/pdftable1/");
//		File targetDir = new File("../../cm-ucl/corpus-oa-pmr-v02/");

		new CProject().run("--project "+targetDir
				+ " --output tableViewList.html"
				+ " --projectMenu .*/tables/tableView.html");
	}
	
	
	@Test
	/** 
	 * tests renaming and moving files
	 */
	@Ignore // LARGE
	public void testMove2() {
		boolean clean = true;
		File sourceDir = new File("../../cm-ucl/corpus-oa-pmr/");
		if (!sourceDir.exists()) {
			LOG.error("no cm-ucl; exiting");
			return;
		}
		File targetDir = new File("../../cm-ucl/corpus-oa-pmr-v02/");
		LOG.debug("copying");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		LOG.debug("copied");
		
		new Norma().run("--project "+targetDir+" --fileFilter ^.*svg/table(\\d+)\\.svg"
				+ " --outputDir "+targetDir
				+ " --move2 tables/table(\\1)/table.svg");
		new Norma().run("--project "+targetDir+" --fileFilter ^.*image/table(\\d+)\\.png"
				+ " --outputDir "+targetDir
				+ " --move2 tables/table(\\1)/table.png");
		new Norma().run("--project "+targetDir+" --fileFilter ^.*pdftable/table(\\d+)\\.annot\\.svg"
				+ " --outputDir "+targetDir
				+ " --move2 tables/table(\\1)/table.annot.png");
	}
	
	@Test
	/** aggregate into TabbedButton display
	 */
	@Ignore // move to production 
	public void testHtmlAggregateCMUCL() {
		boolean clean = true;
		File sourceDir = new File("../../cm-ucl/corpus-oa-pmr/");
		if (!sourceDir.exists()) {
			LOG.error("no cm-ucl; exiting");
			return;
		}
		File targetDir = new File("../../cm-ucl/corpus-oa-pmr-v02/");
		LOG.debug("copying");
		
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		LOG.debug("copied");
		new Norma().run("--project "+targetDir+" --fileFilter ^.*svg/table(\\d+)\\.svg"
				+ " --outputDir "+targetDir
				+ " --move2 tables/table(\\1)/table.svg");
		new Norma().run("--project "+targetDir+" --fileFilter ^.*image/table(\\d+)\\.png"
				+ " --outputDir "+targetDir
				+ " --move2 tables/table(\\1)/table.png");
		new Norma().run("--project "+targetDir+" --fileFilter ^.*pdftable/table(\\d+)\\.annot\\.svg"
				+ " --outputDir "+targetDir
				+ " --move2 tables/table(\\1)/table.annot.png");

		/** make the *.svg.html as we have cleaned the directory */
		new Norma().run("--project "+targetDir+" --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg"
				+ " --outputDir "+targetDir
				+ " --transform svgtable2html");
		
		/** make the *.svg.csv */
		new Norma().run("--project "+targetDir+" --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg"
				+ " --outputDir "+targetDir
				+ " --output table.svg.csv"
				+ " --transform svgtable2csv");
		
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

	@Test
	public void testOutputCSVMini() {
		boolean clean = false;
		File sourceDir = NormaFixtures.TEST_PDFTABLE_DIR;
		File targetDir = new File("target/pdftable1/");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		/** make the *.svg.html as we have cleaned the directory */
		new Norma().run("--project "+targetDir+" --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg.*$"
				+ " --outputDir "+"target/pdftable01/"
				+ " --output table.svg.csv"
				+ " --transform svgtable2csv");

	}

	
	@Test
	@Ignore // move to production
	public void testOutputCSVUC() {
		boolean clean = false;
		File sourceDir = new File("../../cm-ucl/corpus-oa-pmr/");
		if (!sourceDir.exists()) {
			LOG.error("no cm-ucl; exiting");
			return;
		}
		File targetDir = new File("../../cm-ucl/corpus-oa-pmr-v02/");
		LOG.debug("copying");
//		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		LOG.debug("copied");
		new Norma().run("--project "+targetDir+" --fileFilter ^.*svg/table(\\d+)\\.svg"
				+ " --outputDir "+targetDir
				+ " --move2 tables/table(\\1)/table.svg");
		new Norma().run("--project "+targetDir+" --fileFilter ^.*image/table(\\d+)\\.png"
				+ " --outputDir "+targetDir
				+ " --move2 tables/table(\\1)/table.png");
		new Norma().run("--project "+targetDir+" --fileFilter ^.*pdftable/table(\\d+)\\.annot\\.svg"
				+ " --outputDir "+targetDir
				+ " --move2 tables/table(\\1)/table.annot.png");

		/** make the *.svg.html as we have cleaned the directory */
		new Norma().run("--project "+targetDir+" --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg.*$"
				+ " --outputDir "+targetDir
				+ " --output table.svg.csv"
				+ " --transform svgtable2csv");

	}
        
}
