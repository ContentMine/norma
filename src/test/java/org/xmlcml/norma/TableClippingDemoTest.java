package org.xmlcml.norma;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cproject.util.CMineGlobber;
import org.xmlcml.cproject.util.CMineTestFixtures;
import org.xmlcml.cproject.util.Utils;
import org.xmlcml.euclid.Real2;
import org.xmlcml.euclid.Real2Range;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.norma.pubstyle.util.RegionFinder;
import org.xmlcml.svg2xml.page.PageCropper;
import org.xmlcml.svg2xml.page.PageCropper.Units;

/** creates complete workflow for extracting clipped tables.
 * 
 * @author pm286
 *
 */
public class TableClippingDemoTest {
	private static final String LANCET_BOX_FILL = "#b30838";
	public static final Logger LOG = Logger.getLogger(TableClippingDemoTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}
	public static final String FULLTEXT_PAGE = "fulltext-page";
	
	@Test
	public void testTotalClippingWorkflow() throws IOException {
		String root = "tracemonkey-pldi-09";
		File tmFile = NormaFixtures.TEST_DEMO_DIR;
		Assert.assertTrue(""+tmFile, tmFile.exists());
		File targetDir = new File("target/clipping/");
		CMineTestFixtures.cleanAndCopyDir(tmFile, targetDir);
		
		// make project
		String cmd = "--project "+targetDir+" --makeProject (\\1)/fulltext.pdf --fileFilter .*/(.*)\\.pdf";
		new Norma().run(cmd);
		File projectDir = new File(targetDir, root);
		Assert.assertTrue(""+projectDir+" is existing dir", projectDir.exists() && projectDir.isDirectory());
		File renamedFile = new File(projectDir, "fulltext.pdf");
		Assert.assertTrue(""+renamedFile, renamedFile.exists());
		
		// create SVG
		cmd = "--project " + targetDir + " --input fulltext.pdf "+ " --outputDir " + targetDir + " --transform pdf2svg ";
		new Norma().run(cmd);
		File svgDir = new File(projectDir, "svg");
		Assert.assertTrue(""+svgDir+" is existing dir", svgDir.exists() && svgDir.isDirectory());
		File page1Svg = new File(svgDir, FULLTEXT_PAGE+1+".svg");
		Assert.assertTrue(""+page1Svg+" is existing file", page1Svg.exists() && !page1Svg.isDirectory());
		CMineGlobber globber = new CMineGlobber();
		globber.setLocation(svgDir.toString());
                // Handle platform-specific paths
                String sep = File.separator;
                String globbingPatternString = ".*/fulltext\\-page.*\\.svg";
                if (sep.equalsIgnoreCase("\\")) { 
                    String replS = "\\" + sep + "\\" + sep;
                    globbingPatternString = globbingPatternString.replaceAll("/", replS);
                }
		globber.setRegex(globbingPatternString);
		List<File> fulltextFiles = globber.listFiles();
		Assert.assertEquals(14, fulltextFiles.size());
		// now clip
		
		/**
JKB: So far I have done the proof of concept on the one paper and measurements UCL developers sent:

{page: 1, top: 25.9, left: 97.8, width: 15.0, height: 8.5},
{page: 1, top: 117.3, left: 17.6, width: 85.6, height: 79.5}

Units are mm
I have established in test code that the resolution is 72dpi (which is the default for PDF.js)

Y axis is downwards

In my worked example, converting from the JSON data in mm to norma PageCropper TLBR, the boxes become:

// these coordinates are x0, y0 BL (not TL)
(277, 727) (320, 703)  'Type'
(50, 467) (293, 242)   [abstract]

// these coordinates are x0, y0 TL 
(277, 73) (320, 97)  'Type' // width 43, height 24
(50, 333) (293, 558)   [abstract] // width 243 , height 225

This works to crop the areas indicated in red in the image.

(Observation: For the abstract, the change to Arial which is wider than the original font 
means that the RHS of the final letters is cropped.)		 */
		
		PageCropper cropper = new PageCropper();
		cropper.setTLBRUserMediaBox(new Real2(0, 800), new Real2(600, 0));
//		cropper.setTLBRUserMediaBox(new Real2(0, 0), new Real2(600, 800));
		String fileroot = FULLTEXT_PAGE+1;
		File inputFile = new File(svgDir, fileroot + ".svg");
		Assert.assertTrue(""+inputFile+" exists", inputFile.exists());
		cropper.readSVG(inputFile);
//		SVGElement svgElement = SVGElement.readAndCreateSVG(inputFile);
//		List<SVGElement> descendants = cropper.extractDescendants(svgElement);
//		Assert.assertEquals("contained ", 4287, descendants.size());
//		SVGSVG.wrapAndWriteAsSVG(descendants, new File(new File("target/crop/"), "materials-05-00027-page7.raw.svg"));
//		List<SVGElement> contained = cropper.extractContainedElements(descendants);
//		Assert.assertEquals("contained ", 995, contained.size());
//		SVGSVG.wrapAndWriteAsSVG(contained, new File(new File("target/crop/"), "materials-05-00027-page7.crop.svg"));

		// now in mm
		/**
top: 117.3, left: 17.6, width: 85.6, height: 79.5
		 */
		double MM2PX = 72 / 25.4;
		double x0 = 17.6; // mm
		double width = 85.6; // mm
		double x1 = x0 + width;
//		double y0 = 117.3;
		double y0 = (800 / MM2PX) - 117.3; // coordinate system wrong way up // mm
		double height = 79.5; // mm
		double y1 = y0 - height;
//		cropper.setSVGElement(svgElement);
//		SVGElement svgElement = cropper.cropElementTLBR(new Real2(x0, y0), width, height, Units.MM);
		SVGElement svgElement = cropper.cropElementTLBR(new Real2(x0, y0), new Real2(x1, y1), Units.MM);
		Assert.assertNotNull(svgElement);
		File svgfile = new File(new File("target/crop/"), fileroot+".cropmmz.svg");
		LOG.debug("writing "+svgfile);
		SVGSVG.wrapAndWriteAsSVG(svgElement, svgfile);
		Assert.assertTrue(""+svgfile, svgfile.exists());
		
	}
	
	@Test
	@Ignore
	public void testGlobbing() throws IOException {
		File svgDir = new File("target/clipping/tracemonkey-pldi-09", "svg");
		Assert.assertTrue(""+svgDir+" is existing dir", svgDir.exists() && svgDir.isDirectory());
		CMineGlobber globber = new CMineGlobber();
		globber.setLocation(svgDir.toString());
		globber.setRegex(".*/fulltext\\-page.*\\.svg");
		List<File> fulltextFiles = globber.listFiles();
		LOG.debug(fulltextFiles);
		Assert.assertEquals(14, fulltextFiles.size());
	}
	
	@Test
	@Ignore
	public void testCropping() {
		PageCropper cropper = new PageCropper();
		cropper.setTLBRUserMediaBox(new Real2(0, 800), new Real2(600, 0));
		Assert.assertEquals("cropToLocalTransformation", 
			"(1.0,0.0,0.0,\n"
			+ "0.0,-1.0,800.0,\n"
			+ "0.0,0.0,1.0,)",
			cropper.getCropToLocalTransformation().toString());
		// clip a table - cropping coordinates, 
		cropper.setTLBRUserCropBox(new Real2(50, 467), new Real2(293, 242));
		String fileroot = FULLTEXT_PAGE+1;
		File svgDir = new File("target/clipping/tracemonkey-pldi-09/svg/");
		File inputFile = new File(svgDir, fileroot + ".svg");
		Assert.assertTrue(""+inputFile+" exists", inputFile.exists());
		SVGElement svgElement = SVGElement.readAndCreateSVG(inputFile);
		List<SVGElement> descendants = cropper.extractDescendants(svgElement);
		Assert.assertEquals("contained ", 4287, descendants.size());
		SVGSVG.wrapAndWriteAsSVG(descendants, new File(new File("target/crop/"), fileroot+".raw.svg"));
		List<SVGElement> contained = cropper.extractContainedElements(descendants);
		Assert.assertEquals("contained ", 950, contained.size());
		SVGSVG.wrapAndWriteAsSVG(contained, new File(new File("target/crop/"), fileroot+".crop.svg"));
		
		/**
top: 117.3, left: 17.6, width: 85.6, height: 79.5
		 */
		cropper = new PageCropper();
		svgElement = SVGElement.readAndCreateSVG(inputFile);
		cropper.setSVGElementCopy(svgElement);
		double x0 = 117.3;
		double width = 85.6;
		double x1 = x0 + width;
		double y0 = 17.6;
		double height = 79.5;
		double y1 = y0 + height;
		svgElement = cropper.cropElementTLBR(new Real2(x0, y0), new Real2(x1, y1), Units.MM);
		Assert.assertNotNull(svgElement);
		SVGSVG.wrapAndWriteAsSVG(svgElement, new File(new File("target/crop/"), fileroot+".cropmmx.svg"));

	}
	
	@Test
	/** may move elsewhere later
	 * assumes SVG files have been created in target.
	 */
	public void testCroppingArguments() {
		File projectDir = new File("target/clipping/tracemonkey-pldi-09/");
		File svgDir = new File("target/clipping/tracemonkey-pldi-09/svg/");
		String fileroot = FULLTEXT_PAGE+1;
		File inputFile = new File(svgDir, fileroot + ".svg");
//		SVGElement svgElement = SVGElement.readAndCreateSVG(inputFile);
		/**
		double MM2PX = 72 / 25.4;
		double x0 = 17.6; // mm
		double width = 85.6; // mm
		double x1 = x0 + width;
//		double y0 = 117.3;
		double y0 = (800 / MM2PX) - 117.3; // coordinate system wrong way up // mm
		double height = 79.5; // mm
		double y1 = y0 - height;
		 */
		String cmd = "--project "+projectDir +
				" --cropbox x0 17.6 y0 117.3 width 85.6 height 79.5 ydown units mm "+
				" --page 1 "+
				" --mediabox x0 0 y0 0 width 600 height 800 ydown units px " +
				" --output svg/crop1.2.svg"
		;
		Norma norma = new Norma();
		norma.run(cmd);
		Assert.assertTrue(""+inputFile+" exists", inputFile.exists());
	}
	
	@Test
	public void testCompleteDemo() {
		File bmjDir = new File(NormaFixtures.TEST_DEMO_DIR, "bmj");
		File targetDir = new File("target/demos/bmj/");
		String cmd;
		
		CMineTestFixtures.cleanAndCopyDir(bmjDir, targetDir);
		/** ignore while testing */
		cmd = "--project "+targetDir+" --makeProject (\\1)/fulltext.pdf --fileFilter .*/(.*)\\.pdf";
		new Norma().run(cmd);
		cmd = "--project " + targetDir + " --input fulltext.pdf "+ " --outputDir " + targetDir + " --transform pdf2svg ";
		new Norma().run(cmd);
		/**
    UCL-style inputs to crop out Table 4 (units in mm, y is downwards):
    page: 10, top: 15.0, left: 13.0, width: 187.0, height: 60.0
		 */
		File svgFile; SVGElement svgElement; Real2Range box;
		String outpath = Utils.convertPathRegexToCurrentPlatform("svg/crop10.1.svg");
		cmd = "" +
			"--project "+targetDir +
			" --cropbox x0 13.0 y0 15.0 width 187.0 height 60.0 ydown units mm "+
			" --pageNumbers 10 "+
			" --mediabox x0 0 y0 0 width 600 height 800 ydown units px " +
			" --output " + outpath;
		/** skip while testing */
		new Norma().run(cmd);         
		svgFile = new File("target/demos/bmj/10.1136.bmjopen-2016-12335/svg/crop10.1.svg");
		Assert.assertTrue(svgFile.toString()+" exists", svgFile.exists());
		svgElement = SVGElement.readAndCreateSVG(svgFile);
		box = svgElement.getBoundingBox();
		box = box.format(0);
		Assert.assertEquals("box ", "((42.0,553.0),(48.0,195.0))" , box.toString());

		/** lower box */
		outpath = new File("svg/crop10.2.svg").toString();
		cmd = "" +
				"--project "+targetDir +
				" --cropbox x0 44.0 y0 580.0 x1 551.0 y1 736.0 ydown units px "+
				" --pageNumbers 10 "+
				" --mediabox x0 0 y0 0 width 600 height 800 ydown units px " +
				" --output " + outpath;
			new Norma().run(cmd);
			svgFile = new File("target/demos/bmj/10.1136.bmjopen-2016-12335/svg/crop10.2.svg");
			Assert.assertTrue(svgFile.toString()+" exists", svgFile.exists());
			svgElement = SVGElement.readAndCreateSVG(svgFile);
			box = svgElement.getBoundingBox();
			box = box.format(0);
			Assert.assertEquals("box ", "((48.0,547.0),(582.0,733.0))" , box.toString());
		// check that we can create the cTreeLIst (see factory)
	}

	@Test
	public void testGetRegionByXPath() throws IOException {
		File svgDir = new File(NormaFixtures.TEST_DEMO_DIR, "bmj/svg");
		CMineGlobber globber = new CMineGlobber();
                String globberRegex = Utils.convertPathRegexToCurrentPlatform(".*/bmj/svg/");
		globber.setRegex(globberRegex + FULLTEXT_PAGE + "\\d+\\.svg");
		globber.setLocation(svgDir.toString());
		List<File> svgFiles = globber.listFiles();
		Assert.assertEquals("svg",  15, svgFiles.size());
		RegionFinder regionFinder = new RegionFinder();
		// this is BMJ box colour
		String xpath = "*[local-name()='path' and contains(@style, 'fill:#b2ccff;')]";
		regionFinder.setXPath(xpath);
		PageCropper pageCropper = new PageCropper();
		for (int pageNumber = 1; pageNumber < svgFiles.size(); pageNumber++) {
			File svgFile = new File(svgDir, FULLTEXT_PAGE+ pageNumber + ".svg");
			SVGElement svgElement = SVGElement.readAndCreateSVG(svgFile);
			List<SVGElement> subElementList = regionFinder.findXPathRegions(svgElement);
			if (subElementList.size() > 0) {
				LOG.debug("page "+pageNumber+": "+subElementList.size());
				int section = 1;
				for (SVGElement subElement : subElementList) {
					Real2Range bbox = subElement.getBoundingBox().format(0);
					pageCropper.setSVGElementCopy(svgElement);
					pageCropper.setTLBRUserCropBox(bbox);
					pageCropper.detachElementsOutsideBox();
					SVGElement svgElement0 = pageCropper.getSVGElement();
					LOG.debug(bbox + "/" +svgElement0.getBoundingBox());
					if (svgElement0 != null) {
						SVGSVG.wrapAndWriteAsSVG(svgElement0, new File("target/clipping/bmj/", "box."+pageNumber+"."+(section++)+".svg"));
					}
				}
			}
		}
	}
	
	@Test
	@Ignore // NYworking
	public void testGetBoxesByXPath() throws IOException {
		File projectDir = new File(NormaFixtures.TEST_DEMO_DIR, "lancet");
		File targetDir = new File("target/demos/lancet/");
		CMineTestFixtures.cleanAndCopyDir(projectDir, targetDir);
		Norma.convertRawPDFToProjectToSVG(targetDir);
		
		RegionFinder regionFinder = new RegionFinder();
		 // lancet
		regionFinder.setOutputDir("target/clipping/lancet/");
		regionFinder.setRegionPathFill(LANCET_BOX_FILL);
		File[] ctreeDirectories = targetDir.listFiles();
		Assert.assertEquals(3,  ctreeDirectories.length);
		for (File ctreeDirectory : ctreeDirectories) {
			regionFinder.findRegions(ctreeDirectory);
		}
		
	}
	
	@Test
	public void testCTree() {
		File projectDir = new File(NormaFixtures.TEST_DEMO_DIR, "cert");
		File targetDir = new File("target/demos/cert/");
		CMineTestFixtures.cleanAndCopyDir(projectDir, targetDir);
		Norma.convertRawPDFToProjectToSVG(targetDir);
		File ctree = new File(targetDir, "Varga2001");
                String outpath = Utils.convertPathRegexToCurrentPlatform("tables/table1b/table.svg");
		String cmd = "" +
			"--ctree "+ctree +
			" --cropbox x0 70.0 y0 62.0 x1 460 y1 252 "+
			" --pageNumbers 3 "+
			" --output " + outpath;
		new Norma().run(cmd);
	}
		
	@Test
	public void testTablesAndEquations() {
		File projectDir = new File(NormaFixtures.TEST_DEMO_DIR, "cert");
		File targetDir = new File("target/demos/cert/");
		CMineTestFixtures.cleanAndCopyDir(projectDir, targetDir);
		Norma.convertRawPDFToProjectToSVG(targetDir);
		
		File ctreeDir; String cmd;
		
		ctreeDir = new File(targetDir, "Timmermans_etal_2016_B_Cell_Crohns");
		cmd = "--ctree "+ctreeDir +
			" --cropbox x0 32.0 y0 728.0 x1 578 y1 274 yup " + " --pageNumbers 3 "+" --output " + 
                        Utils.convertPathRegexToCurrentPlatform("tables/table1/table.svg");
		new Norma().run(cmd);

		ctreeDir = new File(targetDir, "Varga2001");
		cmd = "--ctree "+ctreeDir +
			" --cropbox x0 70.0 y0 62.0 x1 460 y1 252 "+" --pageNumbers 3 "+" --output " + 
                        Utils.convertPathRegexToCurrentPlatform("tables/table1/table.svg");
		new Norma().run(cmd);

		cmd = "--ctree "+ctreeDir +
			" --cropbox x0 268 y0 481 x1 514 y1 255 yup "+" --pageNumbers 7 "+" --output " + 
                        Utils.convertPathRegexToCurrentPlatform("maths/maths1/maths.svg");
		new Norma().run(cmd);
	}

}
