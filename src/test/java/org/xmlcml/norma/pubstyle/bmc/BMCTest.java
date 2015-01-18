package org.xmlcml.norma.pubstyle.bmc;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlcml.euclid.Real2;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.graphics.svg.SVGG;
import org.xmlcml.graphics.svg.SVGPath;
import org.xmlcml.graphics.svg.SVGSVG;
import org.xmlcml.graphics.svg.SVGText;
import org.xmlcml.graphics.svg.objects.SVGBoxChart;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.xml.XMLUtil;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

public class BMCTest {

	
	private static final Logger LOG = Logger.getLogger(BMCTest.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	@Test
	@Ignore // too long
	public void readProvisionalPDFTest() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(Fixtures.BMC_0277_PDF);
		new File("target/bmc/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/bmc/0277.html"), 1);
		
	}
	

	@Test
	@Ignore // too long - creates the SVG
	public void readBMCTrialsProvisional() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(Fixtures.TEST_BMC_DIR, "1745-6215-15-486.pdf"));
		new File("target/bmc/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/bmc/486.html"), 1);
	}
	
	@Test
	// this one has outline glyphs... :-( // all papers in this journal do :-(
	// 4 boxes and three lines
	public void testExtractFlowChart() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(Fixtures.TEST_BMC_DIR, "1745-6215-15-486.29.0.svg"));
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
		
		LOG.debug(signatureSet);
		for (Entry entry : signatureSet.entrySet()) {
			LOG.debug(entry);
		}
		
	}
}
