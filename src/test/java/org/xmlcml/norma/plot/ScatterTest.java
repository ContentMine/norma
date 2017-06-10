package org.xmlcml.norma.plot;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;

public class ScatterTest {
	private static final Logger LOG = Logger.getLogger(ScatterTest.class);

	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	public void testScatter() {
		File sourceDir = NormaFixtures.TEST_PLOT_DIR;
		File targetDir = new File("target/plot1/");
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		File[] files = targetDir.listFiles();
		for (File file : files) {
			LOG.debug("file "+file);
		}
		String cmd = "--project "+targetDir
//				+ " --fileFilter ^.*tables/table(\\d+)/table(_\\d+)?\\.svg.*$"
				+ " --fileFilter ^.*fulltext\\.svg.*$"
		+ " --outputDir "+"target/plot1/"+" --transform scatter2csv";
		new Norma().run(cmd);
	}

}
