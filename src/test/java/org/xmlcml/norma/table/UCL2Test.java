package org.xmlcml.norma.table;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.graphics.svg.cache.ContentBoxCache;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.svg2xml.table.TableContentCreator;

@Ignore // too long
public class UCL2Test {
	private static final Logger LOG = Logger.getLogger(UCL2Test.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	/** iterate over whole CProject 
	 * 
	 */
	public void testCProjectUCLII() {
		// these ones have single text characters
		File sourceDir = new File("../../cm-uclii-jb/corpus-oa-uclii-01/");
		/** only works for PMR */
		if (sourceDir.exists()) {
//			File targetDir = new File("../../cm-ucl/corpus-oa-pmr-v01/");
			File targetDir = new File("target/corpus-oa-uclii-01/all/");
			CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
			// note historical regex /ctree/table%d.svg
			String cmd = "--project "+targetDir+" --fileFilter ^.*/table\\d+(cont)?/table\\.svg$ --outputDir "+targetDir+" --transform svgtable2html";
			LOG.debug("running norma");
			new Norma().run(cmd);
		} else {
			LOG.debug("no data, skipped");
		}
	}
	
	@Test
	/** iterate over closed access
	 * 
	 * _10.1007.s00038-009-8028-2 
	 *   table 2 has unusual right facet // causes problems because bleeds into header
	 *   table 3 looks OK anyway
	 *   table 4 also looks OK
	 * 
	 */
	public void testCProjectUCLIIClosed() {
		File sourceDir = new File("../../cm-uclii-jb/corpus-closed-uclii-01/");
		/** only works for PMR directory structure */
		if (sourceDir.exists()) {
			File targetDir = new File("target/corpus-closed-uclii-01/");
			CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
			String cmd = "--project "+targetDir+" --fileFilter ^.*/table\\d+(cont)?/table\\.svg$ --outputDir "+targetDir+" --transform svgtable2html";
			LOG.debug("running norma");
			new Norma().run(cmd);
		} else {
			LOG.debug("no data, skipped");
		}
	}
	
	
	// corpus-oa-uclii-01 and possible problems
	@Test
	/** standard THBF sections (Title,Header,Body,Footer) - should work
	 * 
	 */
	public void testCProjectUCLIITHBF() {
		// these ones have single text characters
		File sourceDir = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/thbf/");
		File targetDir = new File("target/corpus-oa-uclii-01/thbf");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		// note historical regex /ctree/table%d.svg
		String cmd = "--project "+targetDir+" --fileFilter ^.*/table\\d+(cont)?/table\\.svg$ --outputDir "+targetDir+" --transform svgtable2html";
		LOG.debug("running norma");
		new Norma().run(cmd);
	}
	
	// corpus-oa-uclii-01 and possible problems
	@Test
	/** HBTF sections (Title,Header,Body,Footer) 
	 * 
	 */
	public void testCProjectUCLIIHBTF() {
		// these ones have single text characters
		File sourceDir = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/hbtf/");
		File targetDir = new File("target/corpus-oa-uclii-01/hbtf");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		// note historical regex /ctree/table%d.svg
		String cmd = "--project "+targetDir+" --fileFilter ^.*/table\\d+(cont)?/table\\.svg$ --outputDir "+targetDir+" --transform svgtable2html";
		LOG.debug("running norma");
		new Norma().run(cmd);
	}
	
	// corpus-oa-uclii-01-problems
	@Test
	/** problems due to boxes
	 * the header is a box, so parse as 
	 *  T box(H) B F
	 */
	public void testCProjectUCLIIBoxed() {
//	break at	TableContentCreator.createSectionsAndRangesArray();
		String type = "boxed";
		File sourceDir = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/" + type + "/");
		File targetDir = new File("target/corpus-oa-uclii-01/" + type);
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		String cmd = "--project "+targetDir+" --fileFilter ^.*/table\\d+(cont)?/table\\.svg$ --outputDir "+targetDir+" --transform svgtable2html";
		LOG.debug("running norma");
		new Norma().run(cmd);
	}

	@Test
	public void testBoxedHeader() {
		String type = "boxed";
		String cproject = "10.1007.s13142-010-0006-y";
		String table = "table1";
		File tableFile = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/" + type + "/" + cproject + "/tables/" + table + "/table.svg");
		TableContentCreator tableContentCreator = TableContentCreator.createHTMLFrom(tableFile);
		tableContentCreator.setContentBoxGridFile(new File("target/contentBox/"+cproject+"/"+table+"Content.svg"));
        tableContentCreator.createHTMLFromSVG(tableFile);
	}
	
	@Test
	public void testBoxedTable() {
		String type = "hbtf";
		String cproject = "10.1016.S2213-2600_14_70195-X";
		String table = "table2";
		File tableFile = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/" + type + "/" + cproject + "/tables/" + table + "/table.svg");
		TableContentCreator tableContentCreator = new TableContentCreator();
		tableContentCreator.setContentBoxGridFile(new File("target/contentBox/"+cproject+"/"+table+"Content.svg"));
        tableContentCreator.createHTMLFromSVG(tableFile);
		ContentBoxCache contentBoxCache = tableContentCreator.getContentBoxCache();
		LOG.debug("ContentBox cache: "+contentBoxCache);
		
	}
	
	@Test
	public void testShortLines() {
		String type = "shortlines";
		File tableFile = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/" + type + "/_bmj.2001.323.1–5/tables/table1/table.svg");
		TableContentCreator tableContentCreator = new TableContentCreator();
        tableContentCreator.createHTMLFromSVG(tableFile);
	}
	
	/** some "lines" are thin rectangles
	 * 
	 */
	@Test
	public void testPathRectLine() {
		String type = "pathRectLine";
		File tableFile = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/" + type + "/_10.1038.ijo.2016.239/tables/table1/table.svg");
		TableContentCreator tableContentCreator = new TableContentCreator();
        tableContentCreator.createHTMLFromSVG(tableFile);
        
	}
	
	@Test
	/** continuation tables
	 * 
	 */
	public void testCProjectUCLIIContinuationAll() {
		// these ones have single text characters
		File sourceDir = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/continue/");
		File targetDir = new File("target/corpus-oa-uclii-01-problem/continue");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		// note historical regex /ctree/table%d.svg
		String cmd = "--project "+targetDir+" --fileFilter ^.*/combined/table\\d+(cont)?/table\\.svg$ --outputDir "+targetDir+" --transform svgtable2html";
		LOG.debug("running norma");
		new Norma().run(cmd);
	}
	
	@Test
	/** Rotated 90 (anticlockwise)
	 * 
	 */
	public void testCProjectUCLIIRot90() {
		runNorma("rot90");
	}
	
	@Test
	/** Unknown problems
	 * 
	 */
	public void testCProjectUCLIIRuleFail() {
		runNorma("rulefail");
	}
	
	@Test
	// this one has three visible rules. Maybe they need joining?
	public void testRuleFail1() {
		File tableFile = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/rulefail/10.1177.0956797611400615/tables/table1/table.svg");
		TableContentCreator tableContentCreator = new TableContentCreator();
        tableContentCreator.createHTMLFromSVG(tableFile);
	}
	
	@Test 
	/** these panels don't make sense.
	 * The fill of several is white and stroke = none so invisible.
	 * the title is in one contentpane and the body + footer in another.
	 * Bad!
	 * Actually works without panels at all.
	 * 
	 * 
	 * 
	 */
	public void testOverlappingPanels() {
		File tableFile = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/panels/_10.1016.j.ypmed.2009.07.022/tables/table2/table.svg");
		TableContentCreator tableContentCreator = new TableContentCreator();
        tableContentCreator.createHTMLFromSVG(tableFile);
	}
	// =====================================
		


	private void runNorma(String dirname) {
		File sourceDir = new File(NormaFixtures.TEST_TABLE_DIR, "corpus-oa-uclii-01/" + dirname + "/");
		File targetDir = new File("target/corpus-oa-uclii-01-problem/" + dirname);
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		String cmd = "--project "+targetDir+" --fileFilter ^.*/table\\d+(cont)?/table\\.svg$ --outputDir "+targetDir+" --transform svgtable2html";
		LOG.debug("running norma");
		new Norma().run(cmd);
	}
	

}
