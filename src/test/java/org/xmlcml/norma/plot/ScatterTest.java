package org.xmlcml.norma.plot;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.plot.SVGMediaBox;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.NormaFixtures;

@Ignore // TOO LONG
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
		LOG.debug("target: "+targetDir);
		String cmd = "--project "+targetDir	+ " --fileFilter ^.*/figure\\d+/figure\\.svg.*$"
		+ " --outputDir "+targetDir+" --transform scatter2csv";
		new Norma().run(cmd);
	}

	
	@Test
	public void testFailing() {
		String[] ctrees = new String[]{
				"13643", // OUTLINE GLYPHS no horizontal or vertical scale text (LEFT rot90) // also SLOW, multiline has 2000 components
				"6400831a", // fullbox null (no horizontal lines) (orange box?)
				"8546", // OK
				"copas", // fullbox null also LEFT rot90
				"hetpub-compact", // Cannot parse, perhaps emDash problem
				"PHM_2011_9", // OK
				"Publicationbias", // OK
				"sjart_st0061", //can't find axis scales // NO Axial TEXT???
				"spiegelhalter", // fullbox null //
				"uk09_palmer_handouts", // Bad axisScale capture RuntimeException: cannot match ticks with values; LEFT tickValues: 0; ticks: 4
		};
		for (String ctree : ctrees) {	
			analyzeRoot("multipleTreesSingleFigure", ctree);
		}
		
	}

	@Test
	public void testFailing1() {
		String[] trees = {
				"10.21053_ceo.2016.9.1.1",
		};
		for (String tree : trees) {	
			analyzeRoot("singleTreeSingleFigure1", tree);
		}
	}

	/** final tilburg
	 * 
	 */
	@Test
	public void testFinalTilburg() {
		File tilburg1 = new File(System.getProperty("user.home")+"/workspace/tilburgrc1/");
		String cprojectName = "corpus";
		File sourceDir = new File(tilburg1, cprojectName);
		File targetDir = new File(SCATTERPLOT_DIR, cprojectName);
		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
		LOG.debug("target: "+targetDir);
		String cmd = "--project "+targetDir	+ " --fileFilter ^.*/figure\\d+/figure\\.svg.*$"
		+ " --outputDir "+targetDir+" --transform scatter2csv";
		new Norma().run(cmd);
	}

//	/** final tilburg
//	 * 
//	 */
//	@Test
//	public void testFinalTilburg() {
//		File tilburg1 = new File(System.getProperty("user.home")+"/workspace/tilburgrc1/");
//		String cprojectName = "corpus";
//		File sourceDir = new File(tilburg1, cprojectName);
//		File targetDir = new File(SCATTERPLOT_DIR, cprojectName);
//		CMineTestFixtures.cleanAndCopyDir(sourceDir, targetDir);
//		LOG.debug("target: "+targetDir);
//		String cmd = "--project "+targetDir	+ " --fileFilter ^.*/figure\\d+/figure\\.svg.*$"
//		+ " --outputDir "+targetDir+" --transform scatter2csv";
//		new Norma().run(cmd);
//	}



	// ====================
	
	private void analyzeRoot(String cproject, String ctree) {
		try {
			File svgFile = new File(NormaFixtures.TEST_PLOT_DIR, cproject+"/"+ctree+"/"+"figures"+"/"+"figure1"+"/"+"figure.svg");
			if (!svgFile.exists()) {
				LOG.error("File does not exist: "+svgFile);
				return;
			}
			LOG.debug(svgFile.getAbsolutePath());
			SVGElement svgElement = SVGElement.readAndCreateSVG(svgFile);
			SVGMediaBox plotBox = new SVGMediaBox();
			plotBox.setSvgOutFile(new File(new File("target/scatterplots/"), ctree+".out.svg"));
			plotBox.setCsvOutFile(new File(new File("target/scatterplots/"), ctree+".out.csv"));
			plotBox.readAndCreateCSVPlot(svgElement);
		} catch (Exception e) {
			LOG.error(ctree+": Exception "+e);
		}
	}
	
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
