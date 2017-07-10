package org.xmlcml.norma.svg;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;

import junit.framework.Assert;

/** test compacting SVG
 * 
 * @author pm286
 *
 */
public class CompactTest {
private static final Logger LOG = Logger.getLogger(CompactTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	//./src/test/resources/org/xmlcml/norma/plot/singleTreeSingleFigure/ctree1/figures/figure1/figure.svg
	@Test
	public void testCompact() {
		String cprojectName = "singleTreeSingleFigure";
		File sourceDir = new File(NormaFixtures.TEST_PLOT_DIR, cprojectName);
		Assert.assertTrue(""+sourceDir +"exists", sourceDir.exists());
		File targetDir = new File("target/compact", cprojectName);
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		String cmd = "--project "+targetDir+" --fileFilter .*/figures/figure(\\d+)/figure.svg --outputDir "+targetDir+" --transform compactsvg";
		File inputFile = new File(targetDir, "ctree1/figures/figure1/figure.svg");
		Assert.assertEquals("input file ", 48389, FileUtils.sizeOf(inputFile));
		new Norma().run(cmd);
		File outputFile = new File(targetDir, "ctree1/figures/figure1/figure.svg.compact.svg");
		long size = FileUtils.sizeOf(outputFile);
//		Assert.assertEquals("new file "+size, 28150, size);
	}
	
}
