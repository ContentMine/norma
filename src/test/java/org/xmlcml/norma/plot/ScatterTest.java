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
	
	File SCATTERPLOT_DIR = new File("target/scatterplots"); 
	
	@Test
	/** a single tree with a single figure file.
	 * 
tilburg00/
	└── ctree1
	    └── figures
	        ├── figure1.svg
	        
	 *
	 * The file is the "bakker" example of a simple scatterplot
	 */
	public void testScatterSingleTreeSingleFigure() {
		File targetDir = createTargetAndReport("singleTreeSingleFigure");
		String cmd = "--project "+targetDir
				+ " --fileFilter ^.*/figure\\d+/figure\\.svg.*$"
		+ " --outputDir "+targetDir+" --transform scatter2csv";
		new Norma().run(cmd);
	}

	
	
	@Test
	/** a single tree with a multiply populated figures/ directory.
	 * 
tilburg0/
	└── ctree1
	    └── figures
	        ├── figure1.svg
	        ├── figure2.svg
	        ├── figure3.svg
	 */
	public void testScatterSingleTreeMultipleFigures() {
		File targetDir = createTargetAndReport("singleTreeMultipleFigures");
		String cmd = "--project "+targetDir
				+ " --fileFilter ^.*/figure\\d+/figure\\.svg.*$"
		+ " --outputDir "+targetDir+" --transform scatter2csv";
		new Norma().run(cmd);
	}

	
	
	@Test
	/**
	 * 
multipleTreesSingleFigure/
	├── bakker
	│   └── figure.svg
	├── calvin
	│   └── figure.svg
	├── dong
	│   └── figure.svg
	├── kerr
	│   └── figure.svg
	├── nair
	│   └── figure.svg
	└── sbarra
	    └── figure.svg
    */
	public void testScatterMultipleTreesSingleFigure() {
		File targetDir = createTargetAndReport("multipleTreesSingleFigure");
		String cmd = "--project "+targetDir
				+ " --fileFilter ^.*/figure\\d+/figure\\.svg.*$"
		+ " --outputDir "+targetDir+" --transform scatter2csv";
		new Norma().run(cmd);
	}

	
	/** this traverses a tree with format:
	 * 
	 *
├── bakker
│   └── figures
│       └── figure1.svg
├── calvin
│   └── figures
│       └── figure2.svg
├── dong
│   └── figures
│       └── figure1.svg
├── joint
│   └── figures
│       ├── figure1.svg
│       ├── figure2.svg
│       ├── figure3.svg
│       ├── figure4.svg
│       ├── figure5.svg
│       └── figure6.svg

	 */
	@Test
	
	public void testScatterMultipleTreesMultipleFigures() {
		File targetDir = createTargetAndReport("multipleTreesMultipleFigures");
		String cmd = "--project "+targetDir
				+ " --fileFilter ^.*/figure\\d+/figure\\.svg.*$"
		+ " --outputDir "+targetDir+" --transform scatter2csv";
		new Norma().run(cmd);
	}

	// ===============================
	
	private File createTargetAndReport(String cprojectName) {
		LOG.debug("\n********** "+cprojectName+" ***********\n");
		File sourceDir = new File(NormaFixtures.TEST_PLOT_DIR, cprojectName);
		File targetDir = new File(SCATTERPLOT_DIR, cprojectName);
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		File[] files = targetDir.listFiles();
		for (File file : files) {
			LOG.debug("file "+file);
		}
		return targetDir;
	}

}
