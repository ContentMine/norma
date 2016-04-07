package org.xmlcml.norma.pubstyle.bmc;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.cmine.files.CTree;
import org.xmlcml.euclid.Real2;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGPath;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.graphics.svg.objects.SVGBoxChart;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.html.HtmlFactory;
import org.xmlcml.html.util.HtmlUtil;
import org.xmlcml.norma.NormaFixtures;
import org.xmlcml.norma.Norma;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.xml.XMLUtil;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

public class BMCTest {

	
	private static final String SRC_MAIN_RESOURCES = "src/main/resources";
	private static final Logger LOG = Logger.getLogger(BMCTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	@Ignore // too long
	public void readProvisionalPDFTest() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(NormaFixtures.BMC_0277_PDF);
		new File("target/bmc/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/bmc/0277.html"), 1);
		
	}
	

	@Test
	@Ignore // too long - creates the SVG // file missing
	public void readBMCTrialsProvisional() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(NormaFixtures.TEST_BMC_DIR, "1745-6215-15-486.pdf"));
		new File("target/bmc/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/bmc/486.html"), 1);
	}
	
	@Test
	@Ignore // too long - creates the SVG // file missing
	public void readBMCLions() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(NormaFixtures.TEST_BMC_DIR, "1745-2148-14-70.pdf"));
		new File("target/bmc/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/bmc/14-170.html"), 1);
	}
	

	
	@Test
	// this one has outline glyphs... :-( // all papers in this journal do :-(
	// 4 boxes and three lines
	public void testExtractFlowChart() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(NormaFixtures.BMC_MISC_DIR, "1745-6215-15-486.29.0.svg"));
		SVGBoxChart boxChart = new SVGBoxChart(rawChart);
		boxChart.createChart();
		List<SVGPath> pathList = boxChart.getSVGPathList();
		Multiset<String> signatureSet = HashMultiset.create();
		Map<String, String> characterByDStringMap = new HashMap<String, String>();
		characterByDStringMap.put("MLCCCCCCLCCCCCCCCCCLLCCCCCCCCCCCCCCCCCCCCCCZMLLCCCCCCCCZ","a");
		characterByDStringMap.put("MCCCCCCCCLCCCCCCCCLCCCCCCCCLCCCCCCCCZMCCCCCCCCLCCCCCCZ","b");
		characterByDStringMap.put("MCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCZ","c");
		characterByDStringMap.put("MLCCCCCCCCLCCCCCCCCCCCCLCCCCCCCCZMLCCCCCCCCCCCCCCZ","d");
		characterByDStringMap.put("MLCCLCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCZMLCCCCCCCZ","e");
		characterByDStringMap.put("MCCCCCCCCCCCCLLCCCCCCLLCCCCCCCCLLCCCCCCLLCCCCCCCCCCZ","f");
		characterByDStringMap.put("MCCLCCCCCCCCCCCCLCCCCCCCCCCCCCCCCCCCCCCCCCCCCLCCZMCCCCCCCCCCCCZMCCLCCCCCCCCCCZ","g");
		characterByDStringMap.put("MLCCCCCCCCLCCCCCCCCZMCCCCCCCCZ","i");
		characterByDStringMap.put("MLCCCCCCCCLCCCCCCLCCCCCCCCLCCCCCCLCCCCCCCCLCCCCCCCCLCCCCCCCCCCCCCCZ","m");
		characterByDStringMap.put("MLCCCCCCCCLCCCCCCLCCCCCCCCLCCCCCCCCLCCCCCCZ","n");
		characterByDStringMap.put("MCCCCCCCCCCCCCCCCZMCCCCCCCCCCCCCCCCZ","o");
		characterByDStringMap.put("MCCCCLLCCCCCCCCLCCLCCCCCCZMCCCCLLLCCCCZ","P");
		characterByDStringMap.put("MCCCCCCCCCCCCLCCCCCCCCLCCCCCCCCLCCCCCCCCCCCCZ","r");
		characterByDStringMap.put("MCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCZ","s");
		characterByDStringMap.put("MCCCCCCCCCCLLCCCCCCLLCCCCCCCCLLCCCCCCLLCCCCCCCCCCZ","t");
		characterByDStringMap.put("MLCCCCCCCCLCCCCCCLCCCCCCCCLCCCCCCLCCCCCCCCZ","u");
		characterByDStringMap.put("MCCCCLCCCCCCCCLCCCCCCCCCCCLLLLCCCCCCCCZ", "v");
		characterByDStringMap.put("MCCLCCCCCCCCLLLLCCCCCCCCLCCCCCCCCCCLLLLCCCCCCCCLLLLCCCCCCCCZ", "w");
		characterByDStringMap.put("MLCCCCCCCCLLCCCCCCCCCLLCCCCCCCCCLLCCCCCCCCCZ", "x");
		characterByDStringMap.put("MLLCCCCCCCLCCLCCCCCCCCLLLCCCCCCCZ","y");
		
		characterByDStringMap.put("MLCCCCCCCCCLLLCCCCCCCCCLCCCCCCCCZMLLLZ", "A");
		characterByDStringMap.put("MCCCCLCCLCCLCCCCCCCCLLLCCCCCCCCLLLCCCCZ","B");
		characterByDStringMap.put("MCCCCCCCCCCCCCCCCCCLLCCCCCCLCCCCLCCCCCCCCCCCCCCCCCCCCZ","G");
		characterByDStringMap.put("MLCCCCCCCCLLLCCCCCCCCLCCCCCCCCLLLCCCCCCCCZ", "H");
		characterByDStringMap.put("MLCCCCCCCCLCCCCCCCCZ","I");
		characterByDStringMap.put("MCCCCLCCLCCCCCCCCLLCCCCZ", "L");
		
		characterByDStringMap.put("MCCCCLCCCCCCCCLLLCCCCCCCCCLCCCCCCCCLLCCCCZ","1");
		characterByDStringMap.put("MCCCCLCCCCCCCCLCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCLLCCCCZ","2");
		characterByDStringMap.put("MCCCCCCCCCCCCCCCCCCCCCCCCCCLCCCCCCCCLCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCLCCCCZ","3");
		characterByDStringMap.put("MCCLLCCCCCCCCLLCCCCCCCCLCCCCCCCCLLCCZMLLLZ","4");
		characterByDStringMap.put("MCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCZMCCCCCCCCCCCCCCCCZ", "6");
		characterByDStringMap.put("MCCCCLCCCCCCCCCLLCCCCCCLCCCCZ", "7");

		characterByDStringMap.put("MCCLCCCCLCCZMCCLCCCCLCCZ","=");

		double fontSize = 16.0;
		double deltay;
		SVGG g = new SVGG();
		for (SVGPath path : pathList) {
			Dimension dim = path.getBoundingBox().format(1).getDimension();
			double h = dim.getHeight();
			if (h <= 15 && dim.getWidth() <= 15) {
				String dString = path.getSignature();
				String character = characterByDStringMap.get(dString);
				path.detach();
				path.setStrokeWidth(0.1);
				path.setFill("cyan");
				g.appendChild(path);
				if ("?".equals(character)) {
					path.setOpacity(0.0);
					signatureSet.add(path.getSignature());
				} else {
					deltay = (h < 10) ? fontSize * 0.60 : fontSize * 0.8;
					path.setOpacity(0.3);
					SVGText text = new SVGText(path.getBoundingBox().getCorners()[0].plus(new Real2(fontSize * (-0.1), deltay * 0.95)), character);
					text.setFontSize(fontSize);
					text.setFill("red");
					text.setOpacity(0.8);
//					path.getParent().replaceChild(path, text);
					g.appendChild(text);
				}
			}
		}
		
		SVGSVG.wrapAndWriteAsSVG(g, new File("target/bmc/boxChart.svg"));
		
//		LOG.debug(signatureSet);
//		for (Entry entry : signatureSet.entrySet()) {
//			LOG.debug(entry);
//		}
		
	}

	/** convert BMC XML to scholarlyHtml.
	 * 
	 * SHOWCASE
	 * 
	 * @throws Exception
	 */
	@Test 
	public void testTransformBMCXMLToHtml() throws Exception {
		// this is a valid CTree
		CTree cTree = new CTree(NormaFixtures.BMC_15_1_511_DIR);
		// it's got 4 files
		Assert.assertEquals("reserved files", 4, cTree.getReservedFileList().size());
		Assert.assertNotNull("fulltext.xml", cTree.getExistingFulltextXML());
		Assert.assertNotNull("fulltext.html", cTree.getExistingFulltextHTML());
		Assert.assertNotNull("fulltext.pdf", cTree.getExistingFulltextPDF());
		Assert.assertNotNull("results.json", cTree.getExistingResultsJSON());
		// these files don't exist yet
		Assert.assertNull("scholarly.html", cTree.getExistingScholarlyHTML());
		Assert.assertNull("results.xml", cTree.getExistingResultsDir());
		// make a copy for the tests
		File cTree1 = new File("target/bmc/15_1_511");
		// clean any existing files
		if (cTree1.exists()) FileUtils.forceDelete(cTree1);
		FileUtils.copyDirectory(NormaFixtures.BMC_15_1_511_DIR, cTree1);
		// now run the transformation
		String[] args = {
				// the cTree directory
				"--ctree", cTree1.toString(),
				// we will transform the fulltext.xml into ...
				"--input", "fulltext.xml",
				// a new scholarly.html
				"--output", "scholarly.html",
				// using a BMC-specifc stylesheet (BMC is not JATS-compliant)
				"--standalone", "true",
				"--transform", SRC_MAIN_RESOURCES+"/org/xmlcml/norma/"+"pubstyle/bmc/xml2html.xsl", // stylesheet to convert 
		};
		// the primary entry point
		Norma norma = new Norma();
		// run() calls parseArgs() which:
		// parses all arguments and checks for consistency of cTree
		// then run() executes all arguments with runMethod. In this case it's transform() (for XSL)
		norma.run(args);
		// this will have created a new scholarlyHtml . The remaing commands are just to verify its content
		// this makes a new object but doesn't affect the filestore
		CTree cTreeNew = new CTree(cTree1);
		// there should be a new scholarly.html
		File scholarlyHtml = cTreeNew.getExistingScholarlyHTML();
		Assert.assertNotNull("scholarly.html", scholarlyHtml);
		// parse it into an HtmlElement we can query
		HtmlElement htmlElement = new HtmlFactory().parse(scholarlyHtml);
		List<HtmlElement> divElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='div']");
		Assert.assertEquals("div elements "+divElements.size(), 216, divElements.size()); 
		List<HtmlElement> spanElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='span']");
		Assert.assertEquals("span elements "+spanElements.size(), 1054, spanElements.size()); 
		List<HtmlElement> pElements = HtmlUtil.getQueryHtmlElements(htmlElement, "//*[local-name()='p']");
		Assert.assertEquals("p elements "+pElements.size(), 144, pElements.size()); 
	}
	
	/** convert BMC XML to scholarlyHtml.
	 * 
	 * SHOWCASE
	 * 
	 * This throws an exception with ONE of the papers - "Missing scheme in absolute URI reference"
	 * not yet dignosed 
	 * 
	 * @throws Exception
	 */
	@Test 
	public void testTransformSeveralXMLToHtml() throws Exception {
		File[] cmFiles = {
				 NormaFixtures.TEST_ELIFE_CTREE0, // this one has bad URI?
				 NormaFixtures.TEST_F1000_CTREE0,
				 NormaFixtures.TEST_FRONTIERS_CTREE0,
				 NormaFixtures.TEST_MDPI_CTREE0,
				 NormaFixtures.TEST_PEERJ_CTREE0,
				 NormaFixtures.TEST_PENSOFT_CTREE0,
				 NormaFixtures.TEST_PLOSONE_CTREE0
		};
		int nqs = cmFiles.length;
		File[] targetFiles = new File[nqs];
		for (int i = 0; i < nqs; i++) {
			CTree cTree = new CTree(cmFiles[i]);
			targetFiles[i] = new File("target/test/file"+i);
			cTree.copyTo(targetFiles[i], true);
		}
		String[] args = {
				"--ctree", 
				// this one fails to parse - raw version has xmlns:hw="org.highwire.hpp" which lacks a protocol
				targetFiles[0].toString(),
				targetFiles[1].toString(),
				targetFiles[2].toString(),
				targetFiles[3].toString(),
				targetFiles[4].toString(),
				targetFiles[5].toString(),
				targetFiles[6].toString(),
				"--input", "fulltext.xml",
				"--output", "scholarly.html",
				"--standalone", "true",
				"--transform", SRC_MAIN_RESOURCES+"/org/xmlcml/norma/"+"pubstyle/nlm/toHtml.xsl", // stylesheet to convert 
		};
		Norma norma = new Norma();
		norma.run(args);
	}

}