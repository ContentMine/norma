package org.xmlcml.norma;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.args.DefaultArgProcessor;

/** tests issues on Github
 * 
 * @author pm286
 *
 */
public class IssueTest {
	
	private static final Logger LOG = Logger.getLogger(IssueTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}


	/**
	 * #3
if --xsl <code> - where code = 'bmc2html', etc fails to resolve the error is not trapped. This causes serious downstream confusion (suggesting there is no input file)
	 */
	@Test
	@Ignore // causes confusion in output
	public void testMissingStyleSheet() throws Exception {
		File targetFile = new File("target/bmc/1471");
		FileUtils.copyDirectory(new File(NormaFixtures.TEST_BMC_DIR, "1471-2148-14-70"), targetFile);
		String args = "-q "+targetFile+" -i fulltext.xml -o fulltext.html --transform bmc2html";
		// OK
		DefaultArgProcessor argProcessor = new NormaArgProcessor(args);
		argProcessor.runAndOutput();
		// bad stylesheet
		FileUtils.copyDirectory(new File(NormaFixtures.TEST_BMC_DIR, "1471-2148-14-70"), targetFile);
		args = "-q "+targetFile+" -i fulltext.xml -o fulltext.html --transform bmc2htmlbad";
		// OK
		argProcessor = new NormaArgProcessor(args);
		try {
			argProcessor.runAndOutput();
//			Assert.fail("should throw exception");
		} catch (RuntimeException re) {
			LOG.debug("Threw expected RuntimeException "+re.getMessage());
		} catch (Exception e) {
//			e.printStackTrace();
			LOG.debug("Threw unexpected RuntimeException "+e.getMessage());
		}
	}
}
