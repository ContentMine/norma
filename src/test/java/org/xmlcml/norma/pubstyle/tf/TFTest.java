package org.xmlcml.norma.pubstyle.tf;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cmine.args.DefaultArgProcessor;
import org.xmlcml.cmine.files.CProject;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.cmine.util.CMineTestFixtures;
import org.xmlcml.norma.NormaArgProcessor;

import junit.framework.Assert;

public class TFTest {
	private static final Logger LOG = Logger.getLogger(TFTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testHtml2Scholarly() {
		File targetDir = new File("target/tutorial/tf");
		CMineTestFixtures.cleanAndCopyDir(new File("src/test/resources/org/xmlcml/norma/pubstyle/tf/TandF_OA_Test"), targetDir);
		String args = "--project "+targetDir+" -i fulltext.html -o scholarly.html --transform jsoup";
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args); 
		argProcessor.runAndOutput(); 
	}
	
	@Test
	public void testHtml2Scholarly2Step() {
		File targetDir = new File("target/tutorial/tf");
		CMineTestFixtures.cleanAndCopyDir(new File("src/test/resources/org/xmlcml/norma/pubstyle/tf/TandF_OA_Test"), targetDir);
		String args = "--project "+targetDir+" -i fulltext.html -o fulltext.xhtml --html jsoup";
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args); 
		argProcessor.runAndOutput(); 
		CProject project = new CProject(targetDir);
		CTree ctree0 = project.getCTreeList().get(0);
		File xhtml = ctree0.getExistingFulltextXHTML();
		Assert.assertTrue("xhtml: ", xhtml.exists());
		args = "--project "+targetDir+" -i fulltext.xhtml -o scholarly.html --transform tf2html";
		argProcessor = new NormaArgProcessor(args); 
		argProcessor.runAndOutput(); 
		File shtml = ctree0.getExistingScholarlyHTML();
		Assert.assertTrue("shtml: ", shtml.exists());
	}
	
	@Test
	@Ignore // data not in norma scope
	public void testConvertJOER() {
		File targetDir = new File("target/tutorial/joer");
		CMineTestFixtures.cleanAndCopyDir(new File("../../../gita/joer"), targetDir);
		String args = "--project "+targetDir+" -i fulltext.html -o fulltext.xhtml --html jsoup";
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args); 
		argProcessor.runAndOutput(); 
		CProject project = new CProject(targetDir);
		CTree ctree0 = project.getCTreeList().get(0);
		File xhtml = ctree0.getExistingFulltextXHTML();
		if (xhtml != null) {
			LOG.debug("OK ");
			Assert.assertTrue("xhtml: ", xhtml != null);
			args = "--project "+targetDir+" -i fulltext.xhtml -o scholarly.html --transform tf2html";
			argProcessor = new NormaArgProcessor(args); 
			argProcessor.runAndOutput(); 
			File shtml = ctree0.getExistingScholarlyHTML();
			Assert.assertTrue("shtml: ", shtml.exists());
		} else {
			LOG.debug("null XHTML");
		}
	}
	
	@Test
	public void convertToc() {
		File targetDir = new File("target/tutorial/tf");
		CMineTestFixtures.cleanAndCopyDir(new File("src/test/resources/org/xmlcml/norma/pubstyle/tf/toc/"), targetDir);
		String args = "--project "+targetDir+" -i fulltext.html -o fulltext.xhtml --html jsoup";
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args); 
		argProcessor.runAndOutput(); 
		args = "--project "+targetDir+" -i fulltext.xhtml -o scholarly.html --transform tf2html";
		argProcessor = new NormaArgProcessor(args); 
		argProcessor.runAndOutput(); 
			
	}
	
}
