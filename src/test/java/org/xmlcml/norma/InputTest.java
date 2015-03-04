package org.xmlcml.norma;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.junit.Test;
import org.xmlcml.args.DefaultArgProcessor;
import org.xmlcml.files.QuickscrapeNorma;
import org.xmlcml.files.QuickscrapeNormaList;

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
		QuickscrapeNormaList qnList = argProcessor.getQuickscrapeNormaList();
		Assert.assertNotNull(qnList);
		Assert.assertEquals("qnlist", 0, qnList.size());
	}
	
	@Test
	public void testMakeSingleQuickscrape() {
		File quickscrapeDir = new File("target/quickscrape/single/");
		FileUtils.deleteQuietly(quickscrapeDir);
		String[] args = {
				"-i", 
				new File(Fixtures.TEST_MISC_DIR, "mdpi-04-00932.xml").toString(),
				"-o", quickscrapeDir.toString()
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 1, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/mdpi-04-00932.xml", inputList.get(0));
		QuickscrapeNormaList qnList = argProcessor.getQuickscrapeNormaList();
		Assert.assertNotNull(qnList);
		Assert.assertEquals("qnlist", 1, qnList.size());
		argProcessor.runAndOutput();
		qnList = argProcessor.getQuickscrapeNormaList();
		Assert.assertEquals("qnlist", 1, qnList.size());
		QuickscrapeNorma qn = qnList.get(0);
		Assert.assertTrue("fulltext.xml", qn.hasFulltextXML());
		Assert.assertFalse("fulltext.html", qn.hasFulltextHTML());
		File fulltextXML = qn.getExistingFulltextXML();
		Assert.assertTrue("fulltextXML", fulltextXML.exists());
		File fulltextHTML = qn.getExisitingFulltextHTML();
		Assert.assertNull("fulltextXML", fulltextHTML);
	}

	
	/** create multiple input directories from multiple input.
	 * 
	 * SHOWCASE
	 */
	@Test
	public void testMakeMultipleQuickscrape() {
		File quickscrapeDir = new File("target/quickscrape/multiple/");
		FileUtils.deleteQuietly(quickscrapeDir);
		String[] args = {
				"-i", 
				new File(Fixtures.TEST_MISC_DIR, "mdpi-04-00932.xml").toString(),
				new File(Fixtures.TEST_MISC_DIR, "peerj-727.xml").toString(),
				new File(Fixtures.TEST_MISC_DIR, "pensoft-4478.xml").toString(),
				"-o", quickscrapeDir.toString()
		};
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		List<String> inputList = argProcessor.getInputList();
		Assert.assertEquals("inputList", 3, inputList.size());
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/mdpi-04-00932.xml", inputList.get(0));
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/peerj-727.xml", inputList.get(1));
		Assert.assertEquals("input file", "src/test/resources/org/xmlcml/norma/miscfiles/pensoft-4478.xml", inputList.get(2));
		argProcessor.runAndOutput();
		QuickscrapeNormaList qnList = argProcessor.getQuickscrapeNormaList();
		Assert.assertEquals("qnlist", 3, qnList.size());
		Assert.assertEquals("qsNorma dir", "target/quickscrape/multiple/mdpi-04-00932", qnList.get(0).getDirectory().toString());
		Assert.assertEquals("qsNorma dir", "target/quickscrape/multiple/peerj-727", qnList.get(1).getDirectory().toString());
		Assert.assertEquals("qnNorma dir", "target/quickscrape/multiple/pensoft-4478", qnList.get(2).getDirectory().toString());
		Assert.assertEquals("filecount0", 1, qnList.get(0).listFiles(false).size());
		Assert.assertEquals("filecount1", 1, qnList.get(1).listFiles(false).size());
		Assert.assertEquals("filecount2", 1, qnList.get(2).listFiles(false).size());
		Assert.assertTrue("fulltext.xml", qnList.get(0).hasFulltextXML());
		Assert.assertTrue("fulltext.xml", qnList.get(1).hasFulltextXML());
		Assert.assertTrue("fulltext.xml", qnList.get(2).hasFulltextXML());
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
	
}
