package org.xmlcml.norma.pubstyle.astrophysj;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;
import org.xmlcml.graphics.svg.SVGElement;
import org.xmlcml.html.HtmlElement;
import org.xmlcml.norma.Fixtures;
import org.xmlcml.norma.SVGPlot;
import org.xmlcml.norma.input.pdf.PDF2XHTMLConverter;
import org.xmlcml.xml.XMLUtil;

public class AstrophysTest {

	@Test
	@Ignore // too long
	public void testReadPDF() throws Exception {
		PDF2XHTMLConverter converter = new PDF2XHTMLConverter();
		HtmlElement htmlElement = converter.readAndConvertToXHTML(new File(Fixtures.TEST_ASTROPHYS_DIR, "0004-637X_754_2_85.pdf"));
		new File("target/astrophys/").mkdirs();
		XMLUtil.debug(htmlElement, new FileOutputStream("target/astrophys/285.html"), 1);
	}
	
	@Test
	public void testExtractPlot() {
		SVGElement rawChart = SVGElement.readAndCreateSVG(new File(Fixtures.TEST_ASTROPHYS_DIR, "754_2_85.fig1.svg"));
		SVGPlot plot = new SVGPlot(rawChart);
		plot.createPlot();
		
	}
	

}
