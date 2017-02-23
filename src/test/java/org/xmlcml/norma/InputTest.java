package org.xmlcml.norma;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.args.DefaultArgProcessor;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.files.CTreeList;

/** tests the various uses of the -i/--input argument.
 * 
 * Fixtures.TEST_INPUT_DIR has a deliberately chaotic directory and naming structure.
 * 
 * @author pm286
 *
 */
public class InputTest {

	
	private static final Logger LOG = Logger.getLogger(InputTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testFilesAndDirectoriesInMiscDirectory() {
		// non-recursive; all includes 3 XML and 1 HTML
		List<File> topLevelFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, null, false));
		Assert.assertEquals("toplevel", 4, topLevelFiles.size());
		// xml suffix
		topLevelFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, new String[]{"xml"}, false));
		Assert.assertEquals("toplevel", 3, topLevelFiles.size());
		// html suffix
		topLevelFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, new String[]{"html"}, false));
		Assert.assertEquals("toplevel", 1, topLevelFiles.size());
		// html and xml suffix
		topLevelFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, new String[]{"html", "xml"}, false));
		Assert.assertEquals("toplevel", 4, topLevelFiles.size());

		// all files
		List<File> allLevelFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, null, true));
		Assert.assertTrue("alllevel", allLevelFiles.size() >= 25);
		// most use XML
		List<File> xmlFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, new String[]{"xml"}, true));
		Assert.assertTrue("allxml", xmlFiles.size() >= 16);
		// Hindawi uses html
		List<File> htmlFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, new String[]{"html"}, true));
		Assert.assertEquals("allhtml", 5, htmlFiles.size());
		// and some from quickscrape
		List<File> jsonFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, new String[]{"json"}, true));
		Assert.assertTrue("json", jsonFiles.size() >= 2);
		List<File> pdfFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, new String[]{"pdf"}, true));
		Assert.assertTrue("pdf", pdfFiles.size() >= 5);
		List<File> epubFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_MISC_DIR, new String[]{"epub"}, true));
		Assert.assertEquals("epub", 1, epubFiles.size());
		// special directory of numbered files
		List<File> numberedFiles = new ArrayList<File>(FileUtils.listFiles(NormaFixtures.TEST_NUMBERED_DIR, new String[]{"xml"}, true));
		Assert.assertEquals("numbered", 5, numberedFiles.size());
	}
	
	@Test
	public void testInputSingleFile() {
		String[] args = {
				"-i", new File(NormaFixtures.TEST_MISC_DIR, "pensoft-4478.xml").toString()
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 1, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/pensoft-4478.xml", inputList.get(0));
	}
	
	@Test
	public void testInputSeveralFiles() {
		String[] args = {
				"-i", 
				new File(NormaFixtures.TEST_MISC_DIR, "mdpi-04-00932.xml").toString(),
				new File(NormaFixtures.TEST_MISC_DIR, "peerj-727.xml").toString(),
				new File(NormaFixtures.TEST_MISC_DIR, "pensoft-4478.xml").toString(),
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 3, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/pensoft-4478.xml", inputList.get(2));
	}
	
	@Test
	public void testInputDirectory() {
		String[] args = {
				"-i", 
				NormaFixtures.TEST_NUMBERED_DIR.toString(),
				"-e", "xml"
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 5, inputList.size());
		// files are not sorted
		Assert.assertTrue("input file", inputList.contains("src/test/resources/org/xmlcml/norma/miscfiles/numbered/nlm1.xml"));
		CTreeList cTreeList = argProcessor.getCTreeList();
		Assert.assertNotNull(cTreeList);
		Assert.assertEquals("cTreelist", 0, cTreeList.size());
	}
	
	/** creates single `CTree` directory
	 * 
	 * // SHOWCASE
	 * Reads isolated XML file, creates a CTree from the name and copies XML file to
	 * fulltext.xml
	 * 
	 * 
	 */
	@Test
	public void testMakeSingleQuickscrape() {
		File cTree = new File("target/quickscrape/single/");
		FileUtils.deleteQuietly(cTree);
		String[] args = {
				"-i", 
				new File(NormaFixtures.TEST_MISC_DIR, "mdpi-04-00932.xml").toString(),
				"-o", cTree.toString()
		};
		// THIS MAKES THE CTREE and copies files and renames
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 1, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/mdpi-04-00932.xml", inputList.get(0));
		CTreeList cTreeList = argProcessor.getCTreeList();
		Assert.assertNotNull(cTreeList);
		Assert.assertEquals("cTreelist", 1, cTreeList.size());
		argProcessor.runAndOutput();
		cTreeList = argProcessor.getCTreeList();
		Assert.assertEquals("cTreelist", 1, cTreeList.size());
		CTree cTree1 = cTreeList.get(0);
		Assert.assertTrue("fulltext.xml", cTree1.hasExistingFulltextXML());
		Assert.assertFalse("fulltext.html", cTree1.hasFulltextHTML());
		File fulltextXML = cTree1.getExistingFulltextXML();
		Assert.assertTrue("fulltextXML", fulltextXML.exists());
		File fulltextHTML = cTree1.getExistingFulltextHTML();
		Assert.assertNull("fulltextXML", fulltextHTML);
	}

	
	/** create multiple input directories from multiple input.
	 * 
	 * No longer works - we need different controls
	 * 
	 */
	@Test
	@Ignore
	public void testMakeMultipleCTree() {
		File cTree = new File("target/quickscrape/multiple/");
		FileUtils.deleteQuietly(cTree);
		String[] args = {
				"-i", 
				new File(NormaFixtures.TEST_MISC_DIR, "mdpi-04-00932.xml").toString(),
				new File(NormaFixtures.TEST_MISC_DIR, "peerj-727.xml").toString(),
				new File(NormaFixtures.TEST_MISC_DIR, "pensoft-4478.xml").toString(),
				"-o", cTree.toString()
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 3, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/mdpi-04-00932.xml", inputList.get(0));
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/peerj-727.xml", inputList.get(1));
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/pensoft-4478.xml", inputList.get(2));
		argProcessor.runAndOutput();
		CTreeList cTreeList = argProcessor.getCTreeList();
		Assert.assertEquals("cTreelist", 3, cTreeList.size());
		Assert.assertEquals("cTree", "target/quickscrape/multiple/mdpi-04-00932", cTreeList.get(0).getDirectory().toString());
		Assert.assertEquals("cTree", "target/quickscrape/multiple/peerj-727", cTreeList.get(1).getDirectory().toString());
		Assert.assertEquals("cTree", "target/quickscrape/multiple/pensoft-4478", cTreeList.get(2).getDirectory().toString());
		Assert.assertEquals("filecount0", 1, cTreeList.get(0).listFiles(false).size());
		Assert.assertEquals("filecount1", 1, cTreeList.get(1).listFiles(false).size());
		Assert.assertEquals("filecount2", 1, cTreeList.get(2).listFiles(false).size());
		Assert.assertTrue("fulltext.xml", cTreeList.get(0).hasExistingFulltextXML());
		Assert.assertTrue("fulltext.xml", cTreeList.get(1).hasExistingFulltextXML());
		Assert.assertTrue("fulltext.xml", cTreeList.get(2).hasExistingFulltextXML());
	}
	

	@Test
	public void testWildcards() {
		String[] args = {
				"-i", 
				NormaFixtures.TEST_NUMBERED_DIR+"/nlm{2,4}.xml"
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		LOG.trace(inputList);
		Assert.assertEquals("inputList", 3, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/numbered/nlm2.xml", inputList.get(0));
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/numbered/nlm4.xml", inputList.get(2));
	}
	
	@Test
	public void testWildcards1() {
		// this doesn't fit the scheme so returns the same
		String[] args = {
				"-i", 
				NormaFixtures.TEST_NUMBERED_DIR+"/nlm{2:4}.xml"
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 1, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/numbered/nlm{2:4}.xml", inputList.get(0));
	}
	
	@Test
	public void testConstraints() {
		// this is OK; 
		try {
			new NormaArgProcessor(new String[] {"-q", "foo"});
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Should not throw exception; "+e);
		}
		// these two are set as incompatible in the args.xml
		try {
			new NormaArgProcessor(new String[] {"-i", "foo", "-q", "bar"});
		} catch (Exception e) {
			Assert.fail("should not throw exception");
		}
		// output without input is ignored
		try {
			new NormaArgProcessor(new String[] {"-o", "foo"});
		} catch (Exception e) {
			Assert.fail("Should not throw exception");
		}
		// quickscrape must not have output;
		try {
			new NormaArgProcessor(new String[] {"-q", "foo", "-o", "bar"});
		} catch (Exception e) {
			Assert.fail("Should throw exception");
		}
		// this is OK; it just doesn't create output
		try {
			new NormaArgProcessor(new String[] {"-i", "bar"});
		} catch (Exception e) {
			Assert.fail("Should not throw exception");
		}
		// this is OK; it would create output
		try {
			new NormaArgProcessor(new String[] {"-i", "bar", "-o", "bar"});
		} catch (Exception e) {
			Assert.fail("Should not throw exception");
		}
	}
	
	@Test
	public void testXMLSuffix() {
		String args = 
			"-i src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/e0119090.xml"+
			" -o target/plos10/";
		Norma norma = new Norma();
		norma.run(args);
	}
	
	@Test
	public void testXMLSuffixAndTransform() {
		String args;
		Norma norma;
		args = 
				"-i src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/e0119090.xml"+
				" -o target/plos10/";
		norma = new Norma();
		norma.run(args);
		args = 
				"-q target/plos10/e0119090/"
				+ " -i fulltext.xml"
				+ " -o scholarly.html"
				+ " --transform nlm2html"
				+ " --standalone true"
				+ "";
		norma = new Norma();
		norma.run(args);
	}
	
	@Test
	public void testSeveralXMLTransform() {
		String args;
		Norma norma;
		File plos10 = new File("target/plos10x/");
		args = 
				"-i"
				+ " src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/e0115544.xml"
				+ " src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/e0116215.xml"
				+" -o target/plos10/";
		norma = new Norma();
		norma.run(args);
		Assert.assertFalse(plos10.exists());
//		args = 
//				"-q"
//				+ " target/plos10/"
//				+ " -i fulltext.xml"
//				+ " -o scholarly.html"
//				+ " --transform nlm2html"
//				+ "";
//		norma = new Norma();
//		norma.run(args);
	}
	
	@Test
	public void testRecursiveTransform() {
		String args;
		Norma norma;
		args = 
				"-i"
				+ " src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/"
				+" -o target/plos10/"
				+ " -r true"
				+ " -e xml"
				+ ""
				+ "";
		norma = new Norma();
		norma.run(args);
		args = 
				"-q"
				+ " target/plos10/"
				+ " -i fulltext.xml"
				+ " -o scholarly.html"
				+ " --transform nlm2html"
				+ " --standalone true"
				+ "";
		norma = new Norma();
		norma.run(args);
	}

	@Test
	@Ignore // FIXME, we need to restructure reserved files
	public void testTeX2HTMLTransform() throws IOException {
		File tempDir = new File("target/tex2html/sample");
		tempDir.mkdirs();
		File inputTex = new File(NormaFixtures.TEST_TEX_DIR, "sample.tex");
		Assert.assertTrue(inputTex.exists());
		// make a CTree 
		FileUtils.copyFile(inputTex, new File(tempDir, "fulltext.tex"));
		CTree cTree = new CTree(tempDir);

		String args;
		Norma norma = new Norma();
		args = "-q " + tempDir.toString()
				+ " -i fulltext.tex"
				+ " -o scholarly.html"
				+ " --transform tex2html";
		norma.run(args);
	}
	
	@Test
	// FIXME
	@Ignore // cannot deal with nonconventional suffixes yet
	public void testNXMLSuffixAndScholarlyHTML() {
		String args = 
			"-i src/test/resources/org/xmlcml/norma/pubstyle/plosone/plos10/PLoS_One_2015_Mar_6_10(3)_e0119090.nxml"+
			" -o target/plos10/";
		Norma norma = new Norma();
		norma.run(args);
	}
}
