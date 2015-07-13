package org.xmlcml.norma;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cmine.args.DefaultArgProcessor;
import org.xmlcml.cmine.files.CMDir;
import org.xmlcml.cmine.files.CMDirList;

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

//	private String testFilePath(String path) {
//		return Fixtures.TEST_NORMA_DIR + "/" + path;
//	}
	
	@Test
	public void testFilesAndDirectoriesInMiscDirectory() {
		// non-recursive; all includes 3 XML and 1 HTML
		List<File> topLevelFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, null, false));
		Assert.assertEquals("toplevel", 4, topLevelFiles.size());
		// xml suffix
		topLevelFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, new String[]{"xml"}, false));
		Assert.assertEquals("toplevel", 3, topLevelFiles.size());
		// html suffix
		topLevelFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, new String[]{"html"}, false));
		Assert.assertEquals("toplevel", 1, topLevelFiles.size());
		// html and xml suffix
		topLevelFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, new String[]{"html", "xml"}, false));
		Assert.assertEquals("toplevel", 4, topLevelFiles.size());

		// all files
		List<File> allLevelFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, null, true));
		Assert.assertEquals("alllevel", 21, allLevelFiles.size());
		// most use XML
		List<File> xmlFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, new String[]{"xml"}, true));
		Assert.assertEquals("allxml", 12, xmlFiles.size());
		// Hindawi uses html
		List<File> htmlFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, new String[]{"html"}, true));
		Assert.assertEquals("allhtml", 5, htmlFiles.size());
		// and some from quickscrape
		List<File> jsonFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, new String[]{"json"}, true));
		Assert.assertEquals("json", 1, jsonFiles.size());
		List<File> pdfFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, new String[]{"pdf"}, true));
		Assert.assertEquals("pdf", 2, pdfFiles.size());
		List<File> epubFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_MISC_DIR, new String[]{"epub"}, true));
		Assert.assertEquals("epub", 1, epubFiles.size());
		// special directory of numbered files
		List<File> numberedFiles = new ArrayList<File>(FileUtils.listFiles(Fixtures.TEST_NUMBERED_DIR, new String[]{"xml"}, true));
		Assert.assertEquals("numbered", 5, numberedFiles.size());
	}
	
	@Test
	public void testInputSingleFile() {
		String[] args = {
				"-i", new File(Fixtures.TEST_MISC_DIR, "pensoft-4478.xml").toString()
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
				new File(Fixtures.TEST_MISC_DIR, "mdpi-04-00932.xml").toString(),
				new File(Fixtures.TEST_MISC_DIR, "peerj-727.xml").toString(),
				new File(Fixtures.TEST_MISC_DIR, "pensoft-4478.xml").toString(),
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
				Fixtures.TEST_NUMBERED_DIR.toString(),
				"-e", "xml"
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 5, inputList.size());
		// files are not sorted
		Assert.assertTrue("input file", inputList.contains("src/test/resources/org/xmlcml/norma/miscfiles/numbered/nlm1.xml"));
		CMDirList cmDirList = argProcessor.getCMDirList();
		Assert.assertNotNull(cmDirList);
		Assert.assertEquals("cmDirlist", 0, cmDirList.size());
	}
	
	/** creates single `CMDir` directory
	 * 
	 * // SHOWCASE
	 * Reads isolated XML file, creates a CMDir from the name and copies XML file to
	 * fulltext.xml
	 * 
	 * 
	 */
	@Test
	public void testMakeSingleQuickscrape() {
		File cmDir = new File("target/quickscrape/single/");
		FileUtils.deleteQuietly(cmDir);
		String[] args = {
				"-i", 
				new File(Fixtures.TEST_MISC_DIR, "mdpi-04-00932.xml").toString(),
				"-o", cmDir.toString()
		};
		// THIS MAKES THE CMDIR and copies files and renames
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 1, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/mdpi-04-00932.xml", inputList.get(0));
		CMDirList cmDirList = argProcessor.getCMDirList();
		Assert.assertNotNull(cmDirList);
		Assert.assertEquals("cmDirlist", 1, cmDirList.size());
		argProcessor.runAndOutput();
		cmDirList = argProcessor.getCMDirList();
		Assert.assertEquals("cmDirlist", 1, cmDirList.size());
		CMDir cmDir1 = cmDirList.get(0);
		Assert.assertTrue("fulltext.xml", cmDir1.hasExistingFulltextXML());
		Assert.assertFalse("fulltext.html", cmDir1.hasFulltextHTML());
		File fulltextXML = cmDir1.getExistingFulltextXML();
		Assert.assertTrue("fulltextXML", fulltextXML.exists());
		File fulltextHTML = cmDir1.getExistingFulltextHTML();
		Assert.assertNull("fulltextXML", fulltextHTML);
	}

	
	/** create multiple input directories from multiple input.
	 * 
	 * No longer works - we need different controls
	 * 
	 */
	@Test
	@Ignore
	public void testMakeMultipleCMDir() {
		File cmDir = new File("target/quickscrape/multiple/");
		FileUtils.deleteQuietly(cmDir);
		String[] args = {
				"-i", 
				new File(Fixtures.TEST_MISC_DIR, "mdpi-04-00932.xml").toString(),
				new File(Fixtures.TEST_MISC_DIR, "peerj-727.xml").toString(),
				new File(Fixtures.TEST_MISC_DIR, "pensoft-4478.xml").toString(),
				"-o", cmDir.toString()
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 3, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/mdpi-04-00932.xml", inputList.get(0));
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/peerj-727.xml", inputList.get(1));
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/pensoft-4478.xml", inputList.get(2));
		argProcessor.runAndOutput();
		CMDirList cmDirList = argProcessor.getCMDirList();
		Assert.assertEquals("cmDirlist", 3, cmDirList.size());
		Assert.assertEquals("cmDir", "target/quickscrape/multiple/mdpi-04-00932", cmDirList.get(0).getDirectory().toString());
		Assert.assertEquals("cmDir", "target/quickscrape/multiple/peerj-727", cmDirList.get(1).getDirectory().toString());
		Assert.assertEquals("cmDir", "target/quickscrape/multiple/pensoft-4478", cmDirList.get(2).getDirectory().toString());
		Assert.assertEquals("filecount0", 1, cmDirList.get(0).listFiles(false).size());
		Assert.assertEquals("filecount1", 1, cmDirList.get(1).listFiles(false).size());
		Assert.assertEquals("filecount2", 1, cmDirList.get(2).listFiles(false).size());
		Assert.assertTrue("fulltext.xml", cmDirList.get(0).hasExistingFulltextXML());
		Assert.assertTrue("fulltext.xml", cmDirList.get(1).hasExistingFulltextXML());
		Assert.assertTrue("fulltext.xml", cmDirList.get(2).hasExistingFulltextXML());
	}
	

	@Test
	public void testWildcards() {
		String[] args = {
				"-i", 
				Fixtures.TEST_NUMBERED_DIR+"/nlm{2,4}.xml"
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		LOG.debug(inputList);
		Assert.assertEquals("inputList", 3, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/numbered/nlm2.xml", inputList.get(0));
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/numbered/nlm4.xml", inputList.get(2));
	}
	
	@Test
	public void testWildcards1() {
		// this doesn't fit the scheme so returns the same
		String[] args = {
				"-i", 
				Fixtures.TEST_NUMBERED_DIR+"/nlm{2:4}.xml"
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
		File inputTex = new File(Fixtures.TEST_TEX_DIR, "sample.tex");
		Assert.assertTrue(inputTex.exists());
		// make a CMDir 
		FileUtils.copyFile(inputTex, new File(tempDir, "fulltext.tex"));
		CMDir cmDir = new CMDir(tempDir);

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
