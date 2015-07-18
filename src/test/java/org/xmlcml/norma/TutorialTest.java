package org.xmlcml.norma;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cmine.files.CMDir;

/** tests Norma.
 * from a set of commandlines
 * 
 */

/**

 * @author pm286
 *
 */
public class TutorialTest {
	
	
	private static final Logger LOG = Logger.getLogger(TutorialTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	/**
	# single XML file
	rm -rf cmdir_xml
	norma -i singleFiles/test_xml_1471-2148-14-70.xml -o cmdir_xml
	*/
	@Test
	public void testConvertSingleFileToCMDirectory() throws Exception {
		File cmdir = new File("target/cmdir_xml/test_xml_1471-2148-14-70");
		if (cmdir.exists()) FileUtils.forceDelete(cmdir);
		Assert.assertFalse(cmdir.exists());
		String args = "-i src/test/resources/org/xmlcml/norma/regressiondemos/singleFiles/test_xml_1471-2148-14-70.xml -o target/cmdir_xml";
		Norma norma = new Norma();
		norma.run(args);
		File output = CMDir.getExistingFulltextXML(cmdir);
		Assert.assertNotNull("fulltextXML", output);
		
	}
	
	/**

	rm -rf cmdirs_xml
	norma -i \
	    singleFiles/test_xml_1471-2148-14-70.xml \
	    singleFiles/plosone_0115884.xml \
		 -o cmdirs_xml
	 */
	@Test
	public void testConvertTwoFilesToCMDirectory() throws Exception {
		File cmdirTop = new File("target/cmdirs_xml");
		if (cmdirTop.exists())FileUtils.forceDelete(cmdirTop);
		Assert.assertFalse(cmdirTop.exists());
		String args = "-i src/test/resources/org/xmlcml/norma/regressiondemos/singleFiles/test_xml_1471-2148-14-70.xml "
				+        "src/test/resources/org/xmlcml/norma/regressiondemos/singleFiles/plosone_0115884.xml "
				+ "-o target/cmdirs_xml --cmdir";
		Norma norma = new Norma();
		norma.run(args);
		Assert.assertTrue(cmdirTop.exists());
	}		
	
	/**

	rm -rf cmdirs_all
	norma -i \
	    	singleFiles/test_xml_1471-2148-14-70.xml \
		    singleFiles/test_pdf_1471-2148-14-70.pdf \
	    	singleFiles/plosone_0115884.xml \
		 -o cmdirs_all
	 */
	@Test
	public void testMixedFilesToCMDirectory() throws Exception {
		File cmdirTop = new File("target/cmdirs_xml");
		if (cmdirTop.exists())FileUtils.forceDelete(cmdirTop);
		Assert.assertFalse(cmdirTop.exists());
		String args = "-i "
				+ "src/test/resources/org/xmlcml/norma/regressiondemos/singleFiles/test_xml_1471-2148-14-70.xml "
				+ "src/test/resources/org/xmlcml/norma/regressiondemos/singleFiles/test_pdf_1471-2148-14-70.pdf "
				+ "src/test/resources/org/xmlcml/norma/regressiondemos/singleFiles/plosone_0115884.xml "
				+ "-o target/cmdirs_xml --cmdir";
		Norma norma = new Norma();
		norma.run(args);
		Assert.assertTrue(cmdirTop.exists());
		Assert.assertNotNull(CMDir.getExistingFulltextXML(new File("target/cmdirs_xml/src_test_resources_org_xmlcml_norma_regressiondemos_singleFiles_plosone_0115884_xml")));
//		Assert.assertNull(CMDir.getExistingFulltextXML(new File("target/cmdirs_xml/test_pdf_1471-2148-14-70")));
		Assert.assertNotNull(CMDir.getExistingFulltextPDF(new File("target/cmdirs_xml/src_test_resources_org_xmlcml_norma_regressiondemos_singleFiles_test_pdf_1471_2148_14_70_pdf")));
		Assert.assertNotNull(CMDir.getExistingFulltextXML(new File("target/cmdirs_xml/src_test_resources_org_xmlcml_norma_regressiondemos_singleFiles_test_xml_1471_2148_14_70_xml")));
	}		
	
	/**

	# convert PDF to TXT (
	norma \
	    	-q cmdirs_all/test_pdf_1471-2148-14-70 \
			--transform pdf2txt \
			-i fulltext.pdf \
			-o fulltext.pdf.txt
	ls -lt cmdirs_all/test_pdf_1471-2148-14-70/fulltext.pdf.txt

	 */
	@Test
	public void testConvertPDF2TXT() throws Exception {
		File cmdirTop = new File("target/cmdirs_all/test_pdf_1471-2148-14-70");
		if (cmdirTop.exists())FileUtils.forceDelete(cmdirTop);
		FileUtils.copyDirectory(new File("src/test/resources/org/xmlcml/norma/regressiondemos/cmdirs_all/test_pdf_1471-2148-14-70"), cmdirTop);
		Assert.assertNotNull("pdf", CMDir.getExistingFulltextPDF(cmdirTop));
		FileUtils.forceDelete(CMDir.getExistingFulltextPDFTXT(cmdirTop));
		String args = "-q target/cmdirs_all/test_pdf_1471-2148-14-70"
				+ " --transform pdf2txt"
				+ " -i fulltext.pdf"
				+ " -o fulltext.pdf.txt";
		Norma norma = new Norma();
		norma.run(args);
		Assert.assertNotNull("pdf", CMDir.getExistingFulltextPDF(cmdirTop));
		Assert.assertNotNull("pdftxt", CMDir.getExistingFulltextPDFTXT(cmdirTop));
	}		
	
	
	/**

	# create scholarly.html for 9 publishers
	 */
	/**
	cp -R quickscrapeDirs/acp/acp-15-1013-2015 temp-acp
	rm temp-acp/scholarly.html
	norma -q temp-acp -i fulltext.xml -o scholarly.html --transform nlm2html
	ls -lt temp-acp/scholarly.html
	 */
	@Test
	@Ignore // not sure what this was meant to do
	public void testConvertACPHTML() throws Exception {
		String sourceName = "src/test/resources/org/xmlcml/norma/quickscrapeDirs/acp/acp-15-1013-2015";
		String destName = "target/cmdirs_all/acp/acp-15-1013-2015";
		testXML2ScholarlyHTML(sourceName, destName);
	}		
	/**
	cp -R src/test/resources/org/xmlcml/norma/pubstyle/bmc/1471-2148-14-70/  temp-bmc
	rm temp-bmc/scholarly.html
	norma -q temp-bmc -i fulltext.xml -o scholarly.html --transform bmc2html
	ls -lt temp-bmc/scholarly.html
	 */
	/**
	cp -R src/test/resources/org/xmlcml/norma/pubstyle/elife/e04407 temp-elife
	rm temp-elife/scholarly.html
	norma -q temp-elife -i fulltext.xml -o scholarly.html --transform nlm2html
	ls -lt temp-elife/scholarly.html
	 */
	/**
	cp -R src/test/resources/org/xmlcml/norma/pubstyle/f1000research/3-190 temp-f1000research
	rm temp-f1000research/scholarly.html
	norma -q temp-f1000research -i fulltext.xml -o scholarly.html --transform nlm2html
	ls -lt temp-f1000research/scholarly.html
	 */
	/**
	cp -R src/test/resources/org/xmlcml/norma/pubstyle/frontiers/fpsyg-05-01582 temp-frontiers
	rm temp-frontiers/scholarly.html
	norma -q temp-frontiers -i fulltext.xml -o scholarly.html --transform nlm2html
	ls -lt temp-frontiers/scholarly.html
	 */
	/**
	cp -R src/test/resources/org/xmlcml/norma/pubstyle/hindawi/247835 temp-hindawi
	rm temp-hindawi/scholarly.html
	norma -q temp-hindawi -i fulltext.xml -o scholarly.html --transform nlm2html
	ls -lt temp-hindawi/scholarly.html
	 */
	/**
	cp -R src/test/resources/org/xmlcml/norma/pubstyle/mdpi/04-00932 temp-mdpi
	rm temp-mdpi/scholarly.html
	norma -q temp-mdpi -i fulltext.xml -o scholarly.html --transform nlm2html
	ls -lt temp-mdpi/scholarly.html
	 */
	/**
	cp -R src/test/resources/org/xmlcml/norma/pubstyle/peerj/727 temp-peerj
	rm temp-peerj/scholarly.html
	norma -q temp-peerj -i fulltext.xml -o scholarly.html --transform nlm2html
	ls -lt temp-peerj/scholarly.html
	 */
	/**
	cp -R src/test/resources/org/xmlcml/norma/pubstyle/pensoft/4478 temp-pensoft
	rm temp-pensoft/scholarly.html
	norma -q temp-pensoft -i fulltext.xml -o scholarly.html --transform nlm2html
	ls -lt temp-pensoft/scholarly.html
	 */
	/**
	cp -R src/test/resources/org/xmlcml/norma/pubstyle/plosone/journal.pone.0115884/ temp-plosone
	rm temp-plosone/scholarly.html
	norma -q temp-plosone -i fulltext.xml -o scholarly.html --transform nlm2html
	ls -lt temp-plosone/scholarly.html
	 */

	private void testXML2ScholarlyHTML(String sourceName, String destName) throws IOException {
		transformNLM(sourceName, destName);
	}

	private void transformNLM(String sourceName, String destName) throws IOException {
		String dtdName = "nlm2html";
		testTransformXML2ScholarlyHTML(sourceName, destName, "fulltext.xml", "scholarly.html", dtdName);
	}

	private void testTransformXML2ScholarlyHTML(String sourceName, String destName, String infile,
			String outfile, String dtdName) throws IOException {
		File cmdirTop = new File(destName);
		if (cmdirTop.exists())FileUtils.forceDelete(cmdirTop);
		FileUtils.copyDirectory(new File(sourceName), cmdirTop);
		Assert.assertNotNull("xml", CMDir.getExistingFulltextXML(cmdirTop));
		FileUtils.forceDelete(CMDir.getExistingFulltextHTML(cmdirTop));
		String args = "-q "+destName
				+ " --transform " + dtdName
				+ " --standalone true"
				+ " -i " + infile
				+ " -o " + outfile;
		Norma norma = new Norma();
		norma.run(args);
		Assert.assertNotNull("html", CMDir.getExistingScholarlyHTML(new File(destName)));
	}
}


