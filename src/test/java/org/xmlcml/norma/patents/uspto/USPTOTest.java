package org.xmlcml.norma.patents.uspto;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.cmine.util.CMineTestFixtures;
import org.xmlcml.norma.NormaArgProcessor;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.util.NormaTestFixtures;


public class USPTOTest {
	private static final Logger LOG = Logger.getLogger(USPTOTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	

	@Test
	public void testReadCTree() throws IOException {
		File target = new File("target/us08978/US08978162-20150317");
		FileUtils.copyDirectory(new File(NormaFixtures.TEST_USPTO08978_DIR, "US08978162-20150317/"), target);
		String args = "-i fulltext.xml --transform uspto2html -o scholarly.html --ctree "+target; 
		NormaArgProcessor norma = new NormaArgProcessor(args);
		norma.runAndOutput();
		NormaTestFixtures.checkScholarlyHtml(target, 
//				"<?xml version=\"1.0\"?>"
//				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head xmlns=\"\">"
//				+ "<style>div {border: 2px solid black; margin: 5pt; padding: 5pt;}</style></head>"
//				+ "<div xmlns=\"\"><h2>us-patent-grant</h2><h1>BIBLIOGRAPHIC</h1><b>US08978162B220150317:::</b><b>document-id:: </b>"
//				+ "<span title=\"country\""
//				);
				"<?xml version=\"1.0\"?>"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
				+ "<head>"
				+ " <style>div {border: 2px solid black; margin: 5pt; padding: 5pt;}</style>"
				+ " </head>"
				+ " <body>"
				+ "<div>"
				+ " <h2>us-patent-grant</h2> <h1>BIBLIOGRAPHIC</h1>"
				+ " <b> US 08978162 B2 20150317 ::: </b> <b>document-id:: </b> <span title=\"country\">U"
				);
	}

	@Test
	public void testReadCProject() throws IOException {
		File target = new File("target/us08978/");
		CMineTestFixtures.cleanAndCopyDir(NormaFixtures.TEST_USPTO08978_DIR, target);
		String args = "-i fulltext.xml --transform uspto2html -o scholarly.html --project "+target; 
		NormaArgProcessor norma = new NormaArgProcessor(args);
		norma.runAndOutput();
		File shtmlFile = new File(target, "US08978162-20150317");
		CProject project = new CProject(target);
		Assert.assertEquals("ctrees", 8, project.getResetCTreeList().size());
		NormaTestFixtures.checkScholarlyHtml(shtmlFile, 
//				"<?xml version=\"1.0\"?>"
//				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">"
//				+ "<head xmlns=\"\"><style>div {border: 2px solid black; margin: 5pt; padding: 5pt;}</style></head>"
//				+ "<div xmlns=\"\">"
//				+ "<h2>us-patent-grant</h2>"
//				+ "<h1>BIBLIOGRAPHIC</h1>"
//				+ "<b>US08978162B220150317:::</b><b>document-id:: </b><span title=\"country\""
//				);
				"<?xml version=\"1.0\"?>"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>"
				+ " <style>div {border: 2px solid black; margin: 5pt; padding: 5pt;}</style>"
				+ " </head> <body><div> <h2>us-patent-grant</h2> "
				+ "<h1>BIBLIOGRAPHIC</h1> <b> US 08978162 B2 20150317 ::: </b>"
				+ " <b>document-id:: </b> <span title=\"country\">U"
				);

	}
}
