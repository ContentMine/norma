package org.xmlcml.norma.pubstyle.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.xmlcml.cproject.args.DefaultArgProcessor;
import org.xmlcml.cproject.files.CProject;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.norma.NormaArgProcessor;

public class XMLCleanerTest {

	@Test
	public void testTidy() throws IOException {
		File sourceDir =  new File("src/test/resources/org/xmlcml/norma/pubstyle/acs/out/");
		File targetDir = new File("target/cleaner");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		// get rid of old files
		File cleanDir = new File(targetDir, "clean");
		FileUtils.deleteDirectory(cleanDir);
		Assert.assertFalse(""+cleanDir.getAbsolutePath(), cleanDir.exists());
		new File(targetDir, "fulltext.xhtml").delete();
		new File(targetDir, "scholarly.html").delete();
		CProject cProject = new CProject(targetDir);
		Assert.assertEquals("cleaner", cProject.getDirectory().getName());
		XMLCleaner cleaner = new XMLCleaner();
		cleaner.setProject(cProject);
		cleaner.tidyHtmlToXHtml(); 
		String abb = "acs";
		String symbol = abb+"2html";
		String args = "--project "+targetDir+" -i fulltext.xhtml -o scholarly.html --transform "+symbol;
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args); 
		argProcessor.runAndOutput(); 
		CTree cTree = cProject.getCTreeByName("jo402790x");
		File shtmlFile = cTree.getExistingScholarlyHTML();
		Assert.assertNotNull(shtmlFile);
		Assert.assertFalse(""+cleanDir.getAbsolutePath(), cleanDir.exists());
	}

	@Test
	public void testTidyTransform() {
		
//		File projectDir = cProject.getDirectory();
//		XMLCleaner cleaner = new XMLCleaner();
//		cleaner.setProject(cProject);
//		cleaner.tidyHtmlToXHtml(); 
//		
//		CTree ctree0 = cProject.getResetCTreeList().get(0);
//		File xhtmlFile = ctree0.getExistingFulltextXHTML();
//		if (xhtmlFile != null) {
//			Assert.assertTrue("xhtml: ", xhtmlFile.exists());
//		String symbol = abb+"2html";
//		String args = "--project "+projectDir+" -i fulltext.xhtml -o scholarly.html --transform "+symbol;
//		NormaArgProcessor argProcessor = new NormaArgProcessor(args); 
//		argProcessor.runAndOutput(); 
//			shtmlFile = ctree0.getExistingScholarlyHTML();
//		}
	}


}
