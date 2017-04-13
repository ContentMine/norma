package org.xmlcml.norma.grobid.runner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.grobid.core.main.batch.GrobidMain;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.files.CProject;
import org.xmlcml.cproject.files.RegexPathFilter;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.norma.NormaFixtures;

import junit.framework.Assert;

@Ignore
public class LargeGrobidRunnerTest {

	private static final Logger LOG = Logger.getLogger(LargeGrobidRunnerTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testSinglePDF() throws Exception {
		File pdfDirectory = new File("src/test/resources/org/xmlcml/norma/grobid/sample/");
		File targetDir = new File(NormaFixtures.TARGET_DIR, "grobid/sample");
		CMineTestFixtures.cleanAndCopyDir(pdfDirectory, targetDir);	
		runGrobid(GrobidRunner.GROBID_HOME, targetDir);
	}

	private void runGrobid(File grobidHome, File pdfDirectory) throws IOException, Exception {
		String cmd = ""
				+ " -exe processFullText"
				+ " -gH " + grobidHome
				+ " -dIn " + pdfDirectory
				+ " -dOut " + pdfDirectory
				+ "";
		String[] args = cmd.split("\\s+");
		GrobidMain.main(args);
	}
	
	@Test
	@Ignore // LARGE
	public void testManyPDF() throws Exception {
		File targetDir = new File(NormaFixtures.TARGET_DIR, "grobid/tutorial0");
		CMineTestFixtures.cleanAndCopyDir(new File(NormaFixtures.TEST_GROBID_DIR, "tutorial0"), targetDir);	
		GrobidRunner grobidRunner = new GrobidRunner();
		grobidRunner.setInputDirectory(targetDir);
		grobidRunner.setOptions(GrobidOption.PROCESS_FULL_TEXT_OPTIONS);
		grobidRunner.run();
		
		List<File> pdfFiles = new RegexPathFilter(".*\\.pdf").listNonDirectoriesRecursively(targetDir);
		Assert.assertEquals(5, pdfFiles.size());
		List<File> teiXmlFiles = new RegexPathFilter(".*\\.tei\\.xml").listNonDirectoriesRecursively(targetDir);
		Assert.assertEquals(5, teiXmlFiles.size());
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testTidyAssets() throws Exception {
		File targetDir = new File(NormaFixtures.TARGET_DIR, "grobid/sample2");
		CMineTestFixtures.cleanAndCopyDir(new File(NormaFixtures.TEST_GROBID_DIR, "sample2"), targetDir);	
		GrobidRunner grobidRunner = new GrobidRunner();
		List<GrobidOption> grobidOptions = Arrays.asList(new GrobidOption[] {GrobidOption.PROCESS_FULL_TEXT});
		grobidRunner.setInputDirectory(targetDir);
		grobidRunner.setOptions(grobidOptions);
		grobidRunner.run();
		CProject cProject = grobidRunner.createProject(targetDir);
		Assert.assertEquals(2, cProject.size());
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testGreek() throws Exception {
		File targetDir = new File(NormaFixtures.TARGET_DIR, "papers_greek1");
		CMineTestFixtures.cleanAndCopyDir(new File(NormaFixtures.TEST_GROBID_DIR, "papers_greek1"), targetDir);	
		GrobidRunner grobidRunner = new GrobidRunner();
		List<GrobidOption> grobidOptions = Arrays.asList(new GrobidOption[] {GrobidOption.PROCESS_FULL_TEXT});
		grobidRunner.setInputDirectory(targetDir);
		grobidRunner.setOptions(grobidOptions);
		grobidRunner.run();
		CProject cProject = grobidRunner.createProject(targetDir);
//		Assert.assertEquals(2, cProject.size());
	}

	/**
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testTheses() throws Exception {
		File targetDir = new File(NormaFixtures.TARGET_DIR, "theses/vtech");
		CMineTestFixtures.cleanAndCopyDir(new File("../projects/vtech/grobid"), targetDir);	
		GrobidRunner grobidRunner = new GrobidRunner();
		List<GrobidOption> grobidOptions = Arrays.asList(new GrobidOption[] {GrobidOption.PROCESS_FULL_TEXT});
		grobidRunner.setInputDirectory(targetDir);
		grobidRunner.setOptions(grobidOptions);
		grobidRunner.run();
		CProject cProject = grobidRunner.createProject(targetDir);
//		Assert.assertEquals(2, cProject.size());
	}


	
}
